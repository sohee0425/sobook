package com.so.book.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/order/*")
@RequiredArgsConstructor
public class AdOrderController {

	private final AdOrderService adOrderService;
	
	// 주문목록
	@GetMapping("/order_list")
	public void order_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date, Model model) throws Exception {
		
		log.info("검색정보:" + cri);
		log.info("날짜검색: " + period);
		log.info("날짜검색: " + start_date);
		log.info("날짜검색: " + end_date);
		
		cri.setPerPageNum(5);
		
		List<Map<String, Object>> order_list = adOrderService.order_list(cri, period, start_date, end_date);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adOrderService.getTotalCount(cri, period, start_date, end_date));
		
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	
}
