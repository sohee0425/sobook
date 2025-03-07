package com.so.book.cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.FileUtils;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/cart/*")
@RequiredArgsConstructor
@Controller
public class CartController {
	
	private final CartService cartService;
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVo vo, HttpSession session) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		cartService.cart_add(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}

	// 장바구니 목록
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		List<Map<String, Object>> cart_list = cartService.cart_list(mem_id);
		
		// 날짜폴더의 역슬래쉬 \ 를 / 로 변환작업
		cart_list.forEach(cartVO -> {
			cartVO.put("pro_up_folder", cartVO.get("pro_up_folder").toString().replace("\\", "/"));
			
		});
		
		model.addAttribute("cart_list", cart_list);
		
		// 장바구니 비우기에서 총 금액 null 발생 > 타임리프에서 null 체크 작업 필요
		model.addAttribute("cart_total_price", cartService.getCartTotalPriceByUserId(mem_id));
	}
	
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		cartService.cart_empty(mem_id);
		
		return "redirect:/cart/cart_list";
	}
	
	@GetMapping("/cart_change")
	public String cart_change(CartVo vo, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		 System.out.println("수량 변경 - 상품 코드: " + vo.getPro_code() + ", 변경된 수량: " + vo.getCart_amount());
		
		cartService.cart_change(vo);
		
		
		return "redirect:/cart/cart_list";
		
	}
	
	@PostMapping("/cart_sel_delete")
	public String cart_sel_delete(int[] check, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		cartService.cart_sel_delete(check, mem_id);
		
		return "redirect:/cart/cart_list";
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
