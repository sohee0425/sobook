package com.so.book.wish;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wish/*")
public class WishController {

	private final WishService wishService;
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	// 찜 추가
	@PostMapping("/wish_add")
	public ResponseEntity<String> wish_add(WishVo vo, HttpSession session) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		wishService.wish_add(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 찜 목록
	@GetMapping("/wish_list")
	public void wish_list(HttpSession session, Model model, SearchCriteria cri) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		cri.setPerPageNum(5);
		
		List<Map<String, Object>> wish_list = wishService.wish_list(mem_id, cri);
		
		// 날짜폴더의 역슬래쉬 \ 를 / 로 변환작업
		wish_list.forEach(WishVo -> {
			WishVo.put("pro_up_folder", WishVo.get("pro_up_folder").toString().replace("\\", "/"));
		});
		
		model.addAttribute("wish_list", wish_list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(wishService.getLikeLIstByUserid(mem_id));
		
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	// 위시 리스트 선택 삭제
	@PostMapping("/wish_sel_delete")
	public String wish_sel_delete(int[] check, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		wishService.wish_sel_delete(check, mem_id);
		
		return "redirect:/wish/wish_list";
	}
	
	// 위시 리스트 선택 장바구니
	@PostMapping("/wish_sel_cart_add")
	public String wish_sel_cart_add(@RequestParam("pro_code_arr") int[] pro_code_arr, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		wishService.wish_sel_cart_add(pro_code_arr, mem_id);
		
		return "redirect:/cart/cart_list";
	}
	
	// 선택 주문
	@PostMapping("/wish_sel_order_add")
	public String wish_sel_order_add(@RequestParam("pro_code_arr") int[] pro_code_arr, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		wishService.wish_sel_order_add(pro_code_arr, mem_id);
		
		return "redirect:/order/order_info";
	}
	
	// 위시 리스트 삭제
	@GetMapping("/wish_delete")
	public String wish_delete(SearchCriteria cri, Integer pro_code, RedirectAttributes rttr) throws Exception {
		
		wishService.wish_delete(pro_code);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("pro_code", pro_code);

		
		return "redirect:/wish/wish_list";
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
