package com.so.book.notice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.so.book.admin.notice.AdNoticeService;
import com.so.book.admin.notice.NoticeVo;
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
	
	@GetMapping("/notice_list")
	public void list(Model model, SearchCriteria cri, String ntc_title) throws Exception {
		
		cri.setPerPageNum(Constants.NOTICE_LIST_PAGE_SIZE);
		
		// 글 목록
		model.addAttribute("list", adNoticeService.listAll(cri));
		
		// 페이징
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setDisplayPageNum(Constants.NOTICE_LIST_PAGE_SIZE);
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(noticeService.getTotalNotice(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@GetMapping("/notice_read")
	public void notice_read(@RequestParam("ntc_bno") int ntc_bno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
//		System.out.println("viewAdd 호출됨: ntc_bno = " + ntc_bno);
		
		NoticeVo noticeVo = noticeService.notice_read(ntc_bno);
		model.addAttribute("NoticeVo", noticeVo);
	}
}
