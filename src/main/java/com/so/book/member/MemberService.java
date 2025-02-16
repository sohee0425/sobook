package com.so.book.member;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

// 표준 패키지 구조에서는 인터페이스로 생성하였으나, 현 구조에서는 클래스로 생성함.
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberMapper memberMapper;
	
	
	public String idCheck(String mem_id) {
		return memberMapper.idCheck(mem_id);
	}
	
	
	public void join(MemberVo vo) {
		memberMapper.join(vo);
	}
	
	public MemberVo login(String mem_id) {
		return memberMapper.login(mem_id);
	}
	
	public MemberVo modify(String mem_id) {
		return memberMapper.modify(mem_id);
	}
	
	public void modify_save(MemberVo vo) {
		memberMapper.modify_save(vo);
	}
	
	public void pwchange(String mem_id, String mem_pw) {
		memberMapper.pwchange(mem_id, mem_pw);
	}
	
	public String idsearch(String mem_name, String mem_email) {
		return memberMapper.idsearch(mem_name, mem_email);
	}
	
	public String pwtemp_confirm(String mem_id, String mem_email) {
		return memberMapper.pwtemp_confirm(mem_id, mem_email);
	}
	
	public void delete(String mem_id) {
		memberMapper.delete(mem_id);
	}
	
	public List<Map<String, Object>> my_review(String mem_id, SearchCriteria cri) {
		return memberMapper.my_review(mem_id, cri);
	}
	
	public int getTotalReviewCountByUserId(String mem_id) {
		return memberMapper.getTotalReviewCountByUserId(mem_id);
	}
	
	public List<Map<String, Object>> my_qna(String mem_id, SearchCriteria cri) {
		return memberMapper.my_qna(mem_id, cri);
	}
	
	public int getTotalQnaCount(String mem_id) {
		return memberMapper.getTotalQnaCount(mem_id);
	}
}
