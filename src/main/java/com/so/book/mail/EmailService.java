package com.so.book.mail;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 생성자 생성이 된다. 의존성주입
@Service
public class EmailService {

	private static final int AUTH_CODE_LENGTH = 8;
	private static final String UTF_8_ENCODING = "UTF-8";
	
	private final JavaMailSender mailSender; //G-Mail SMTP 메일서버정보
	private final SpringTemplateEngine templateEngine; // 타임리프 템플릿 엔진

	public void sendEmail(EmailDTO dto, String htmlcontent) {
		
		try {
			// MimeMessage : 메일구성정보를 관리(받는사람, 보내는사람, 받는사람 메일주소, 본문내용)
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, UTF_8_ENCODING);
				
				// 메일 받는 주소
				mimeMessageHelper.setTo(dto.getReceiverMail());
				// 메일 보내는 주소및이름 
				mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
				// 메일 제목
				mimeMessageHelper.setSubject(dto.getSubject());
				
				// 메일 본문내용.  true : 보내는 내용이 html tag가 존재할 경우 태그효과
				mimeMessageHelper.setText(htmlcontent, true);
				
				// 메일발송기능
				mailSender.send(mimeMessage);
		}catch(Exception ex) { 
			log.error("메일 발송 실패 : {}", ex.getMessage(), ex);
			throw new RuntimeException("메일 발송 중 오류가 발생했습니다.", ex);
		}
		
	}
	
	// 단순 메세지 포함한 메일 발송
	public void sendMail(String type, EmailDTO dto, String message) {
		String htmlcontent = generateHtmlContent(message, type);
		sendEmail(dto, htmlcontent);
	}
	
	// 주문내역 메일 발송
	public void sendMail(String type, EmailDTO dto, List<Map<String, Object>> orderInfo, int orderTotalPrice) {
		String htmlContent = generateOrderHtmlContent(orderInfo, orderTotalPrice, type);
		sendEmail(dto, htmlContent);
	}
	
	
	// 인증코드및 임시비밀번호 생성(숫자, 영문대소문자를 이용하여 8개의 문자추출)
	public String createAuthCode() {
		Random random = new Random(); // 무작위 작업.
		StringBuffer key = new StringBuffer();
		
		for(int i=0; i<8; i++) {
			int index = random.nextInt(4);
			
			switch(index) {
				case 0: key.append((char) ((int) random.nextInt(26) + 97)); break; // 영문 소문자
				case 1: key.append((char) ((int) random.nextInt(26) + 65)); break; // 영문 대문자
				default: key.append(random.nextInt(9)); // 숫자(0~8)
			}
		}
		
		return key.toString();
	}
	
	
	// 메일 템플릿사용(thymeleaf 사용) 일반용
	public String generateHtmlContent(String message, String templateName) {
		Context context = new Context();
		
		// 타임리프페이지 "message" 이름으로 데이타를 전달
		context.setVariable("message", message);
		// type : "authcode" -> authcode.html
		return templateEngine.process(templateName, context); // 서버에서 타임리프 파일이 실행되어 html code결과가 생성된다.
	}
	
	// 타임리프 이용 주문 내역용
	private String generateOrderHtmlContent(List<Map<String, Object>> orderInfo, int orderTotalPrice, String templateName) {
		Context context = new Context();
		context.setVariable("order_info", orderInfo);
		context.setVariable("order_total_price", orderTotalPrice);
		return templateEngine.process(templateName, context);
	}
	
}
