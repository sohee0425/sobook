package com.so.book.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// HttpServletRequest request : 클라이언트가 요청한 모든 정보(데이터)를 서버에서 관리하는 객체
		// HttpServletResponse response : 서버에서 클라이언트로 보낼 응답 정보를 관리하는 객체
		
		boolean result = false;
		
		// 인증된 상태인지 체크하는 작업
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo)session.getAttribute("login_auth");
		
		if(memberVo == null) { // 로그인 x (인증을 안한 상태), 로그인 페이지로 처리
			result = false;
			
			// ajax 요청주소인지 구분하는 작업
			if(isAjaxRequest(request)) {
				response.sendError(400); // 400 에러정보를 가지고 제어가 ajax로 넘어감
			}else {
				getTargetUrl(request); // 원래 요청된 주소를 세션형태로 저장 "targetUrl"
				
				response.sendRedirect("/member/login"); // 로그인주소로 넘어감
			}
		}else {	// 로그인 o (인증을 한 상태)
			result = true; // true : 컨트롤러로 주소 실행
		}
		
		
		return result;
	}

	// 클라이언트의 요청이 ajax인지 체크하는 기능
	private boolean isAjaxRequest(HttpServletRequest request) {
		
		boolean isAjax = false;
		
		String header = request.getHeader("AJAX"); // "true"
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		
		return isAjax;
	}

	// 인증되지 않은 상태에서 원래 요청한 주소(URI)의 정보를 저장하는 기능 작업
	private void getTargetUrl(HttpServletRequest request) {
		
		// 주소요청
		String uri = request.getRequestURI(); 
		String query = request.getQueryString(); // ? 뒤의 문자열
		
		if(query == null || query.equals("null")) { // 쿼리스트링이 없을 경우
			query = "";
		}else { // 쿼리스트링이 있을 경우
			query = "?" + query;
		}
		
		String targetUrl = uri + query;
		
		// 클라이언트가 요청한 방식이 get 방식인 경우
		if(request.getMethod().equals("GET")) {
			// 인증되지 않은 사용자가 원래요청한 주소 및 쿼리스트링을 세션에 저장하고 로그인에서 참조하고자 할 경우 목적
			request.getSession().setAttribute("targetUrl", targetUrl);
		}
		
		
		
	}

	// 아래 2개 메서드 미사용 시 삭제
	// 컨트롤러의 요청주소가 호출이 된 후 실행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	// 뷰(타임리프) 렌더링 완료 후 호출
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
