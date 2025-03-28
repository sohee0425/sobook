package com.so.book.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface MemberMapper {

	String idCheck(String mem_id);
	
	void join(MemberVo vo);
	
	// 로그인 
	MemberVo login(String mem_id);
	
	// 회원수정 폼
	MemberVo modify(String mem_id);
	
	// 회원수정
	void modify_save(MemberVo vo);
	
	// mapper인터페이스에서 파라미터가 2개이상이면, @Param 어노테이션을 사용해야 한다.
	// 비밀번호 변경
	void pwchange(@Param("mem_id") String mem_id, @Param("mem_pw") String mem_pw);
	
	String idsearch(@Param("mem_name") String mem_name, @Param("mem_email") String mem_email);
	
	String pwtemp_confirm(@Param("mem_id") String mem_id, @Param("mem_email") String mem_email);
	 
	// 회원 탈퇴
	void delete(String mem_id);
	
	// 리뷰 목록
	List<Map<String, Object>> my_review(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
	
	int getTotalReviewCountByUserId(String mem_id);

	// qna 목록
	List<Map<String, Object>> my_qna(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
	
	int getTotalQnaCount(String mem_id);
}
