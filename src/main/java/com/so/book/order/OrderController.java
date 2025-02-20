package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.so.book.cart.CartService;
import com.so.book.cart.CartVo;
import com.so.book.common.constants.Constants;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.mail.EmailDTO;
import com.so.book.mail.EmailService;
import com.so.book.member.MemberService;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/order/*")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final MemberService memberService;
	private final EmailService emailService;
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	// 사용자 주문목록에서 이미지 또는 상품명 클릭시 상품상세로 진행할 때 필요한 서브카테고리 준비 작업
	@GetMapping("/pro_perinfo")
	public String pro_perinfo(Integer pro_code, RedirectAttributes rttr) throws Exception {
		
		String subCategoryName = orderService.getCategoryNameByPro_code(pro_code);
		
		rttr.addAttribute("cate_name", subCategoryName);
		rttr.addAttribute("pro_code", pro_code);
		
		return "redirect:/product/pro_info";
		
		
	}
	
	// 1) 장바구니에서 주문 클릭 2) 상품목록, 상품상세에서 바로구매 클릭시
	@GetMapping("/order_info")
	public void order_info(CartVo vo, String type, HttpSession session, Model model) throws Exception {
		
		// 장바구니 추가
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		if(type.equals("buy")) cartService.cart_add(vo);
		
		// 장바구니 내역 출력
		List<Map<String, Object>> cartDetails = cartService.getCartDetailsByUserId(mem_id);
		
		cartDetails.forEach(CartVo -> {
			CartVo.put("pro_up_folder", CartVo.get("pro_up_folder".toString().replace("\\", "/")));
		});
		
		String item_name = "";
		
		if(cartDetails.size() == 1) {
			item_name = (String) cartDetails.get(0).get("pro_name");
		}else {
			item_name = (String) cartDetails.get(0).get("pro_name") + " 외 " + cartDetails.size();
		}
		
		model.addAttribute("item_name", item_name);
		model.addAttribute("quantity", cartDetails.size());
		
		model.addAttribute("cartDetails", cartDetails);
		// 총 주문금액
		model.addAttribute("order_total_price", cartService.getCartTotalPriceByUserId(mem_id));
		
		
		// 로그인 사용자 정보
		MemberVo memberVo = memberService.modify(mem_id);
		model.addAttribute("memberVo", memberVo);
		
	}
	
	@PostMapping("/order_save")
	public String order_save(OrderVo vo, HttpSession session, String p_method, String account_transfer, String sender, RedirectAttributes rttr) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		String p_method_info = p_method + "/" + account_transfer + "/" + sender;
		
		orderService.order_process(vo, mem_id, p_method_info);
		
		rttr.addAttribute("ord_code", vo.getOrd_code());
		
		return "redirect:/order/order_result";
	}
	
	int order_total_price;
	
	// 주문 결과 내역
	@GetMapping("/order_result")
	public void order_result(Integer ord_code, Model model) throws Exception {
		
		order_total_price = 0;
		
		// 주문결과내역(주문번호)
		List<Map<String, Object>> order_info = orderService.getOrderInfoByOrd_code(ord_code);
		
		// 날짜폴더 \ > / 변환
		order_info.forEach(o_Info -> {
			o_Info.put("pro_up_folder", o_Info.get("pro_up_folder").toString().replace("\\", "/"));
			order_total_price += ((int) o_Info.get("dt_amount") * (int) o_Info.get("dt_price"));
		});
		
		EmailDTO dto = new EmailDTO("SOBOOK", "SOBOOK", "thlgml@naver.com", "[SOBOOK]주문내역", "주문내역");
		
		emailService.sendMail("mail/orderConfirmation", dto, order_info, order_total_price);
		
		log.info("총주문금액:" + order_total_price);
		
		model.addAttribute("order_info", order_info);
		model.addAttribute("order_total_price", order_total_price);
		
	}
	
	// 주문목록
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("cri") SearchCriteria cri, HttpSession session, Model model, @ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		cri.setPerPageNum(5);
		
		List<Map<String, Object>> order_list = orderService.getOrderInfoByUser_id(mem_id, cri);
		
		order_list.forEach(o_Info -> {
			o_Info.put("pro_up_folder", o_Info.get("pro_up_folder").toString().replace("\\", "/"));			
		});
		
		model.addAttribute("order_list", order_list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(orderService.getOrderCountByUser_id(mem_id));
		
		model.addAttribute("pageMaker", pageMaker);
		
		
	}
	
	// 주문내역 상세 정보
	@GetMapping("/order_detail_info")
	public void order_detail_info(Integer ord_code, Model model) throws Exception {
		
		// 주문 상세 목록
		List<Map<String, Object>> order_product_info = orderService.order_detail_info(ord_code);
		
		// 날짜폴더 역슬래시를 변환
		order_product_info.forEach(o_Info -> {
					o_Info.put("pro_up_folder", o_Info.get("pro_up_folder").toString().replace("\\", "/"));			
				});
		
		model.addAttribute("order_product_info", order_product_info);
		
		// 결제내역
		model.addAttribute("payment_info", orderService.payment_info(ord_code));
		
		// 배송지 정보
		OrderVo order_delivery_info = orderService.order_delivery_info(ord_code);
		model.addAttribute("order_delivery_info", order_delivery_info);
		
	}
	
	
	
	// 배송지 변경
	@PostMapping("/order_info_edit")
	public ResponseEntity<String> order_info_edit(OrderVo vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		/* 주문 상태 확인해서 배송 중 전까지만 배송지 변경하게 하고싶다...
	    String delyStatus = orderService.getOrderStatus(vo.getOrd_code());  // 주문 상태 조회
	    
	 // 배송준비 상태일 때만 수정 가능
	    if ("배송준비".equals(delyStatus)) {  
	        orderService.order_info_edit(vo);  // 배송지 정보 수정
	        entity = new ResponseEntity<String>("success", HttpStatus.OK);  // 성공 응답
	    } else {
	        // 배송준비중 상태가 아니면 수정 불가
	        entity = new ResponseEntity<String>("배송지 변경은 배송준비 상태에서만 가능합니다.", HttpStatus.BAD_REQUEST);
	    }

	    return entity;
		orderService.order_info_edit(vo);*/
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
		
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
