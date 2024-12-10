package com.so.book.member;

import java.net.Authenticator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.so.book.mail.EmailDTO;
import com.so.book.mail.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/member/*")
@Slf4j
@Controller
public class MemberController {

	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final MemberService memberService;
	
	// 회원가입 폼
	@GetMapping("/join")  //  /member/join.html
	public void join() {
		
	}
	
	// 회원정보 저장
	@PostMapping("/join")
	public String join(MemberVo vo) {
		
		vo.setMem_pw(passwordEncoder.encode(vo.getMem_pw()));
		
		memberService.join(vo);
		
		return "redirect:/member/login";
	}
	
	
	// 아이디 중복체크
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(String mem_id) throws Exception {
			
		ResponseEntity<String> entity = null;
			
		String isUse = "";
			
		if(memberService.idCheck(mem_id) != null) {
			isUse = "no"; // 아이디 사용불가능
		}else {
			isUse = "yes"; // 아이디 사용가능
		}
			
		entity = new ResponseEntity<String>(isUse, HttpStatus.OK);
			
		return entity;
	}
	
//	
	
	
	// 로그인 폼
		@GetMapping("/login")
		public void loginForm() {
			
		}
		
	// 로그인 처리
	@PostMapping("/login")
	public String loginProcess(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		MemberVo memberVo = memberService.login(dto.getMem_id());
		
		String url = "";
		String status = "";
		if(memberVo != null) {
			if(passwordEncoder.matches(dto.getMem_pw(), memberVo.getMem_pw())) {
				session.setAttribute("login_auth", memberVo);
				
				url = "/";
			}else {
				status = "pwFail";
				url = "/member/login";
			}
		}else {
			status = "idFail";
			url = "/member/login";
		}
		
		if(session.getAttribute("targetUrl") != null) {
			url = (String)session.getAttribute("targetUrl");
		}
		
		rttr.addFlashAttribute("status", status);
		
		return "redirect:" + url;
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원 수정폼
	@GetMapping("/modify")
	public void modify(HttpSession session, Model model) throws Exception {
		
		String mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
		
		MemberVo memberVo = memberService.modify(mem_id);
		
		model.addAttribute("memberVo", memberVo);
	}
	
	// 회원 수정 저장하기
	@PostMapping("/modify")
	public String modify(MemberVo vo) throws Exception {
		memberService.modify_save(vo);
		
		return "redirect:/member/mypage";
	}
	
	// 마이페이지
	@GetMapping("/mypage")
	public void mypage() throws Exception {
	
	}
	
	// 비밀번호 변경하기 폼
	@GetMapping("/pwchange")
	public void pwchange() throws Exception {
		
	}
	
	// 비밀번호 변경하기
	@PostMapping("/pwchange")
	public String pwchange(@RequestParam("cur_pw") String mem_pw, String new_pw, 
										HttpSession session, RedirectAttributes rttr) throws Exception {
		String url = "";
		String msg = "";
		
		// 암호화된 비밀번호.
		String db_pw = ((MemberVo) session.getAttribute("login_auth")).getMem_pw();
		String mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
		String mem_mail = ((MemberVo) session.getAttribute("login_auth")).getMem_email();
		
		if(passwordEncoder.matches(mem_pw, db_pw)) {
			
			// 60바이트로 암호화
			String encode_new_pw = passwordEncoder.encode(new_pw);
			memberService.pwchange(mem_id, encode_new_pw);
			
			url = "/"; // 메인페이지로 이동
			msg = "success";
			
			// 비밀번호 변경알림 메일발송
			String type = "mail/pwchange";
			
			EmailDTO dto = new EmailDTO();
			dto.setReceiverMail(mem_mail); // 받는사람 메일주소
			dto.setSubject("SoBook 비밀번호 변경알림을 보냅니다.");
			
			emailService.sendMail(type, dto, new_pw);
			
		}else {
			url = "/member/pwchange"; // 비밀번호 변경 폼주소로 이동
			msg = "fail";
		}
		
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	// 아이디및비밀번호 찾기 폼
	@GetMapping("/lostpass")
	public String lostpass() throws Exception {
			
		return "/member/lostpass";
	}
		
	// 아이디 찾기
	@GetMapping("/idsearch")
	public ResponseEntity<String> idsearch(String mem_name, String mem_email) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String result = "";
		
		String mem_id = memberService.idsearch(mem_name, mem_email);
		
		if(mem_id != null) {
			
			String type = "mail/idsearch";
			
			EmailDTO dto = new EmailDTO();
			dto.setReceiverMail(mem_email); // 받는사람 메일주소
			dto.setSubject("SoBook 아이디 찾기결과를 보냅니다.");
			
			result = "success";
			emailService.sendMail(type, dto, mem_id);
		}else {
			result = "fail";
		}
		
		entity = new ResponseEntity<String>(result, HttpStatus.OK);
		
		return entity;
		}
	
	// 임시비밀번호 발급 - 메일발송
	@GetMapping("/pwtemp")
		public ResponseEntity<String> pwtemp(String mem_id, String mem_email) throws Exception {
			
			ResponseEntity<String> entity = null;
			
			String result = "";
			
			String d_mem_email = memberService.pwtemp_confirm(mem_id, mem_email);
			
			if(d_mem_email != null) {
				result = "success";

				String imsi_pw = ((EmailService) emailService).createAuthCode();
				
				// u_id, imsi_pw 암호화
				memberService.pwchange(mem_id,  passwordEncoder.encode(imsi_pw));
				
				
				// 아이디 메일발송
				String type = "mail/pwtemp";
				
				EmailDTO dto = new EmailDTO();
				dto.setReceiverMail(d_mem_email);// 받는사람 메일주소
				dto.setSubject("SoBook 임시비밀번호를 보냅니다.");

				emailService.sendMail(type, dto, imsi_pw);
				
			}else {
				result = "fail";
			}
			
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
			
			return entity;
		}
	
	// 회원 탈퇴
	@GetMapping("/delete")
	public void delete() {
		
	}
	
	@PostMapping("/delete")
	public String deleteOk(String mem_pw, HttpSession session, RedirectAttributes rttr) throws Exception {
	    
		String mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
		
		MemberVo vo = memberService.login(mem_id);
		
		String msg = "";
		String url = "/";
		
		if(vo != null) {
			if(passwordEncoder.matches(mem_pw, vo.getMem_pw())) {
				
				memberService.delete(mem_id);
				session.invalidate();
			}else {
				msg = "pwFail";
				url = "/member/delete";
				
				rttr.addFlashAttribute("msg", msg);
			}
		}
		return "redirect:" + url;
	}

	// 회원 수정하기 전 비밀번호 확인
//	@GetMapping("/checkPwd")
//	public void checkPwd() {
//		
//	}
//	
//	@PostMapping("/checkPwd")
//	public String checkPwdOk(String mem_pw, HttpSession session, RedirectAttributes rttr) throws Exception {
//		
//		String mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
//		
//		
//		return "";
//	}

	
}
