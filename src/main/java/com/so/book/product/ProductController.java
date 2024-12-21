package com.so.book.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.admin.category.AdCategoryService;
import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product/*")
@RequiredArgsConstructor
@Controller
public class ProductController {

	private final ProductService productService;
	private final AdCategoryService adCategoryService;
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@Value("${com.ezen.upload.ckeditor.path}")
	private String uploadCKPath;
	
	@GetMapping("/pro_list")
	public void pro_list(SearchCriteria cri, String cate_name, Integer cate_code, Model model) throws Exception {
		
		// 1차 카테고리목록
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
		
		// 2차 카테고리 상품목록 연결
		model.addAttribute("pro_list", productService.getProductListBysecondCategory(cri, cate_code));
		
		// 페이징 작업
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(productService.getCountProductListBysecondCategory(cate_code));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
	
	// 상품 상세설명
	@GetMapping("/pro_info")
	public void pro_info(@ModelAttribute("cate_name") String cate_name, Integer pro_code, Model model) throws Exception {
		
		ProductVo productVo = productService.pro_info(pro_code);
		
		// 이미지 파일 날짜폴더 변환
		productVo.setPro_up_folder(productVo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("productVo", productVo);
	}
	
}
