package com.so.book.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.cart.CartService;
import com.so.book.cart.CartVo;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/order/*")
@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	
	@GetMapping("/order_info")
	public void order_info(CartVo vo, HttpSession session, Model model) throws Exception {
		
		// 장바구니 추가
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		cartService.cart_add(vo);
		
		// 장바구니 내역 출력
	}
}
