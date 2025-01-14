package com.so.book.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface AdOrderMapper {

	List<Map<String, Object>> order_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	int getTotalCount(@Param("cri") SearchCriteria cri, @Param("period") String period,  @Param("start_date") String start_date, @Param("end_date") String end_date);
}
