package com.so.book.admin.order;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberService;
import com.so.book.order.OrderVo;
import com.so.book.payment.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/order/*")
@RequiredArgsConstructor
public class AdOrderController {

	private final AdOrderService adOrderService;
	private final MemberService memberService;
	private final PaymentService paymentService;
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	// 주문목록
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date, @ModelAttribute("payment_method") String payment_method, 
						@ModelAttribute("payment_status") String payment_status, @ModelAttribute("ord_status") String ord_status,  Model model) throws Exception {
		
		log.info("검색정보:" + cri);
		log.info("날짜검색: " + period);
		log.info("날짜검색: " + start_date);
		log.info("날짜검색: " + end_date);
		log.info("결제방식 " + payment_method);
		log.info("결제상태 " + payment_status);
		log.info("주문현황 " + ord_status);
		
		cri.setPerPageNum(5);
		
		List<Map<String, Object>> order_list = adOrderService.order_list(cri, period, start_date, end_date, payment_method, payment_status, ord_status);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adOrderService.getTotalCount(cri, period, start_date, end_date, payment_method, payment_status, ord_status));
		
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	// 주문 상세 정보
	@GetMapping("/orderdetail_info")
	public void orderdetail_info(Integer ord_code, Model model) throws Exception {
		
		// 1 주문 상세 목록
		List<Map<String, Object>> order_product_info = adOrderService.orderdetail_info(ord_code);
		
		// 날짜폴더 역슬래시를 변환
		order_product_info.forEach(o_Info -> {
			o_Info.put("pro_up_folder", o_Info.get("pro_up_folder").toString().replace("\\", "/"));			
		});
		
		
		model.addAttribute("order_product_info", order_product_info);
		
		// 2 주문 결제내역
		model.addAttribute("payment_info", adOrderService.payment_info(ord_code));
		
		// 3 관리자 메모 , 배송지 정보
		OrderVo order_info = adOrderService.order_info(ord_code);
		model.addAttribute("order_info", order_info);
		
		// 주문자 정보
		String mem_id = order_info.getMem_id();
		model.addAttribute("member_info", memberService.modify(mem_id));
	}
	
	// 상품 개별 삭제
	@PostMapping("/order_product_del")
	public ResponseEntity<String> order_product_del(Integer ord_code, Integer pro_code) throws Exception {
		ResponseEntity<String> entity = null;
		
		adOrderService.order_product_del(ord_code, pro_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 결제상태 변경
	@PostMapping("/payment_status")
	public ResponseEntity<String> payment_status(Integer payment_id, String payment_status) throws Exception {
		ResponseEntity<String> entity = null;
		
		paymentService.payment_status(payment_id, payment_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 배송지 변경
	@PostMapping("/order_info_edit")
	public ResponseEntity<String> order_info_edit(OrderVo vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		adOrderService.order_info_edit(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 관리자 메모 저장
	@PostMapping("/admin_ord_message")
	public ResponseEntity<String> admin_ord_message(Integer ord_code, String ord_message) throws Exception {
		ResponseEntity<String> entity = null;
		
		adOrderService.admin_ord_message(ord_code, ord_message);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 주문상태변경
	@PostMapping("/order_status")
	public ResponseEntity<String> order_status(Integer ord_code, String ord_status) throws Exception {
		ResponseEntity<String> entity = null;
		
		adOrderService.order_status(ord_code, ord_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
