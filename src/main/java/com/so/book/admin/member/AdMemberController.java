package com.so.book.admin.member;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/member/*")
@RequiredArgsConstructor
public class AdMemberController {
	
	private final AdMemberService adMemberService;

	// 회원목록
	@GetMapping("/member_list")
	public void member_list(Model model, @ModelAttribute("cri") SearchCriteria cri,@ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, 
					@ModelAttribute("end_date") String end_date, @ModelAttribute("mem_id") String mem_id, @ModelAttribute("mem_code") String mem_code) {
		
	
		
		List<MemberVo> member_list = adMemberService.member_list(cri, period, start_date, end_date, mem_id, mem_code);
				
		
		PageMaker pageMaker = new PageMaker(); 
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adMemberService.mem_count(cri, period, start_date, end_date, mem_id, mem_code));
		
		model.addAttribute("member_list", member_list);
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	// 회원 상세 정보
	@GetMapping("/member_detail_info")
	public void member_detail_info(Integer mem_code, Model model) {
		
		// 회원 상세 목록
		List<Map<String, Object>> member_detail_info = adMemberService.member_detail_info(mem_code);
		
		model.addAttribute("member_detail_info", member_detail_info);
	}
	
	// 회원 상세 정보 변경
	@PostMapping("/detail_save")
	public ResponseEntity<String> detail_save(@RequestBody MemberVo vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		adMemberService.detail_save(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
}
