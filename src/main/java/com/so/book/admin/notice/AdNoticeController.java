package com.so.book.admin.notice;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ProcessHandle.Info;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.so.book.common.constants.Constants;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
	public String registerPOST(NoticeVo vo, MultipartFile list_img_upload) throws Exception {
		
		
		// 글 정보 DB저장
		adNoticeService.save(vo);
		
		return "redirect:/admin/notice/list";
	}
	
	// 글 목록 list
	@GetMapping("/list")
	public void listAll(Model model, SearchCriteria cri) throws Exception {
		
//		List<NoticeVo> list = adNoticeService.listAll();
		
		// 글 목록
		model.addAttribute("list", adNoticeService.listAll(cri));
		
		
		// 페이징
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setDisplayPageNum(Constants.ADMIN_NOTICE_LIST_PAGE_SIZE);
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adNoticeService.getTotalCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
	// 글 조회
	@GetMapping("/read")
	public void read(int ntc_bno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
		
		NoticeVo noticeVo = adNoticeService.read(ntc_bno);
		model.addAttribute("NoticeVo", noticeVo);
	}
	
	// 글 수정 불러오기
	@GetMapping("/edit")
	public String edit(Model model, @RequestParam("ntc_bno") int ntc_bno, SearchCriteria cri) throws Exception {
		NoticeVo noticeVo = adNoticeService.edit(ntc_bno);
		model.addAttribute("NoticeVo", noticeVo);
		model.addAttribute("cri", cri);
		return "/admin/notice/edit";
	}
	
	// 글 수정 저장
	@PostMapping("/edit")
	public String edit(NoticeVo vo, SearchCriteria cri, RedirectAttributes rttr) throws Exception {
		
//		System.out.println("수정데이터"+vo);
//		  System.out.println("ntc_bno 값: " + vo.getNtc_bno());
		adNoticeService.edit_save(vo);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/admin/notice/list";
	}
	
	
	
	
	
	
}
