package com.so.book.admin.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/statist/*")
@Controller
public class StatisticsController {
	
	private final StatisticsService statisticsService;
	
	// 기본페이지
	@GetMapping("/order_statist")
	public void order_statist() {
		
	}
	
	// ajax 요청으로 차트에 사용할 데이터
	@GetMapping("/monthlysales")
	public ResponseEntity<Map<String, Object>> monthlysales_statistics(Integer year) throws Exception {
		log.info("년도" + year);
		
		// 특정년도의 월별 매출현황
		List<OrderAmount> monthlysales = statisticsService.monthlysales_statistics(year);
		
		// 차트 레이블과 데이터 포맷에 맞게 작업
		Map<String, Object> response = new HashMap<>();
		
		// 차트에 넣을 데이터 구성 작업
		response.put("labels", monthlysales.stream().map(OrderAmount::getMonth).toArray());
		response.put("data", monthlysales.stream().map(OrderAmount::getAmount).toArray());
		
		return ResponseEntity.ok(response);
	}

}
