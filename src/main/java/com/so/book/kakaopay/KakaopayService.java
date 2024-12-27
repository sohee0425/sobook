package com.so.book.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class KakaopayService {
	
	@Value("${readUrl}")
	private String readUrl;
	
	@Value("${approveUrl}")
	private String approveUrl;
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${cid}")
	private String cid;
	
	@Value("${approval}")
	private String approval;
	
	@Value("${cancel}")
	private String cancel;
	
	@Value("${fail}")
	private String fail;
	
	private String tid;
	
	private String partner_order_id;
	
	private String partner_user_id;
	
	// 1차 결제 요청
	public ReadyResponse ready(String partner_order_id, String partner_user_id, String item_name,
				Integer quantity, Integer total_amount, Integer tax_free_amount) {
		
		HttpHeaders headers = getHttpHeaders();
		
		ReadyRequest readyRequest = ReadyRequest.builder()
				.cid(cid)
				.partner_order_id(partner_order_id)
				.partner_user_id(partner_user_id)
				.item_name(item_name)
				.quantity(quantity)
				.total_amount(total_amount)
				.tax_free_amount(tax_free_amount)
				.approval_url(approval)
				.cancel_url(cancel)
				.fail_url(fail)
				.build();
		
		HttpEntity<ReadyRequest> entityMap = new HttpEntity<>(readyRequest, headers);
		
		ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(readUrl, entityMap, ReadyResponse.class);
		
		ReadyResponse readyResponse = response.getBody();
		
		this.tid = readyResponse.getTid();
		this.partner_order_id = partner_order_id;
		this.partner_user_id = partner_user_id;
		
		return readyResponse;
	}
	
	// 2차 요청(결제승인 요청 approve)
	public String approve(String pg_token) {
		// 1)Request header
		HttpHeaders headers = getHttpHeaders();
		
		// 2)Request param
		ApproveRequest approveRequest = ApproveRequest.builder()
				.cid(cid)
				.tid(tid)
				.partner_order_id(partner_order_id)
				.partner_user_id(partner_user_id)
				.pg_token(pg_token)
				.build();
		
		HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
		
		// 결제 준비요청
		ResponseEntity<ApproveResponse> response = new RestTemplate().postForEntity(approveUrl, entityMap, ApproveResponse.class);
		
		log.info("결제승인요청응답 : " + response);
		return "";
	}
	
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + secretKey);
		headers.set("Content-Type", "application/json;charset=utf-8");
		
		
		return headers;
	}
}
