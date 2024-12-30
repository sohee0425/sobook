package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.cart.CartService;
import com.so.book.cart.CartVo;
import com.so.book.common.utils.FileUtils;
import com.so.book.member.MemberService;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/order/*")
@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final MemberService memberService;
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@GetMapping("/order_info")
	public void order_info(CartVo vo, HttpSession session, Model model) throws Exception {
		
		// 장바구니 추가
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		cartService.cart_add(vo);
		
		// 장바구니 내역 출력
		List<Map<String, Object>> cartDetails = cartService.getCartDetailsByUserId(mem_id);
		
		cartDetails.forEach(CartVo -> {
			CartVo.put("pro_up_folder", CartVo.get("pro_up_folder".toString().replace("\\", "/")));
		});
		model.addAttribute("cartDetails", cartDetails);
		
		// 총 주문금액
		model.addAttribute("order_total_price", cartService.getCartTotalPriceByUserId(mem_id));
		
		
		// 로그인 사용자 정보
		MemberVo memberVo = memberService.modify(mem_id);
		model.addAttribute("memberVo", memberVo);
		
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
		
		model.addAttribute("order_info", order_info);
		model.addAttribute("order_total_price", order_total_price);
		
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
