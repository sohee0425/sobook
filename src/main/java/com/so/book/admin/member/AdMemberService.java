package com.so.book.admin.member;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;
import com.so.book.payment.PaymentVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdMemberService {
	
	private final AdMemberMapper adMemberMapper;
	
	public List<MemberVo> member_list(SearchCriteria cri, String period, String start_date, String end_date, String mem_id, String mem_code) {
		return adMemberMapper.member_list(cri, period, start_date, end_date, mem_id, mem_code);
	}
	
	public int mem_count(SearchCriteria cri, String period, String start_date, String end_date, String mem_id, String mem_code) {
		return adMemberMapper.mem_count(cri, period, start_date, end_date, mem_id, mem_code);
	}

	public List<Map<String, Object>> member_detail_info(Integer mem_code) {
		return adMemberMapper.member_detail_info(mem_code);
	}
	
	
	public void detail_save(MemberVo vo) {
		adMemberMapper.detail_save(vo);
	}
}
