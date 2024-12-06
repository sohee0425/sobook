package com.so.book.member;

import org.springframework.stereotype.Service;

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
}
