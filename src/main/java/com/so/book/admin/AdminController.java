package com.so.book.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {

	private final AdminService adminService;
	private final PasswordEncoder passwordEncoder;
	
	// 로그인 주소
	@GetMapping("/")
	public String admin_login() {
		
		return "/admin/adLogin";
	}
	
	@PostMapping("/admin_ok")
	public String admin_ok(AdminDTO dto, HttpSession session, RedirectAttributes rttr) {
		
		AdminDTO db_vo = adminService.admin_ok(dto.getAdm_id());
		
		String url = "";
		String msg = "";
		if(db_vo != null) {
			if(passwordEncoder.matches(dto.getAdm_pw(), db_vo.getAdm_pw())) {
				session.setAttribute("admin_auth", db_vo);
				url = "/admin/ad_menu";
			}else {
				url = "/admin/";
				msg = "pwFail";
			} 
		}else {
			url = "/admin/";
			msg = "idFail";
		}
		
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	@GetMapping("/ad_menu")
	public String menu() {
		return "/admin/ad_menu";
	}
}
