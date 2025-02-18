package com.so.book.admin.statistics;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

	private final StatisticsMapper statisticsMapper;
	
	public List<OrderAmount> monthlysales_statistics(int year) {
		return statisticsMapper.monthlysales_statistics(year);
	}
}
