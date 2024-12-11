package com.so.book.admin.notice;

import java.lang.ProcessHandle.Info;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/notice/*")
@RequiredArgsConstructor
public class AdNoticeController {

	private final AdNoticeService adNoticeService;
	
	// 기본 주소
	@GetMapping("/main")
	public void main() {
		// list를 메인으로 할까? 고민
	}
	
	// 글쓰기 폼
	@GetMapping("/register")
	public void registerGET() throws Exception {
		
	}
	
	// 글쓰기 저장 save
	@PostMapping("/register")
	public String registerPOST(NoticeVo vo) throws Exception {
		
		adNoticeService.save(vo);
		
		return "redirect:/admin/notice/list";
	}
	
	// 글 목록 list
	@GetMapping("/list")
	public void listAll(Model model) throws Exception {
		
//		List<NoticeVo> list = adNoticeService.listAll();
		
		model.addAttribute("list", adNoticeService.listAll());
	}
	
	// 글 조회
	@GetMapping("/read")
	public void read(int ntc_bno, Model model) throws Exception {
		
		NoticeVo noticeVo = adNoticeService.read(ntc_bno);
		model.addAttribute("NoticeVo", noticeVo);
//		model.addAttribute("noticeVo", adNoticeService.read(ntc_bno));
	}
	
	
	
	
}
