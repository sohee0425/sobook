package com.so.book.notice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.admin.notice.AdNoticeService;
import com.so.book.common.constants.Constants;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice/*")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final AdNoticeService adNoticeService;
	
	@GetMapping("/list")
	public void list(Model model, SearchCriteria cri, String ntc_title, int ntc_bno) throws Exception {
		// 글 목록
		model.addAttribute("list", adNoticeService.listAll(cri));
		
		// 페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(noticeService.getTotalNotice(ntc_bno));
		
		model.addAttribute("pageMaker", pageMaker);
	}
}
