package com.so.book.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdOrderService {

	private final AdOrderMapper adOrderMapper;
	
	public List<Map<String, Object>> order_list(SearchCriteria cri, String period, String start_date, String end_date) {
		return adOrderMapper.order_list(cri, period, start_date, end_date);
	}
	
	public int getTotalCount(SearchCriteria cri, String period, String start_date, String end_date) {
		return adOrderMapper.getTotalCount(cri, period, start_date, end_date);
	}
}
