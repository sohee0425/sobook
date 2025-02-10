package com.so.book.admin.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;

public interface AdMemberMapper {
	
	List<MemberVo> member_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, 
			@Param("end_date") String end_date, @Param("mem_id") String mem_id, @Param("mem_code") String mem_code);
	
	int mem_count(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, 
			@Param("end_date") String end_date, @Param("mem_id") String mem_id, @Param("mem_code") String mem_code );
	
	List<Map<String, Object>> member_detail_info(Integer mem_code);
	
	void detail_save(MemberVo vo);
}
