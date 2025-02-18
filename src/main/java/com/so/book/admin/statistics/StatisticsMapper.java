package com.so.book.admin.statistics;

import java.util.List;

public interface StatisticsMapper {
	
	List<OrderAmount> monthlysales_statistics(int year);

}
