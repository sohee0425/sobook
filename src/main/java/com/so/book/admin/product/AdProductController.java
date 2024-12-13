package com.so.book.admin.product;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.so.book.admin.category.AdCategoryService;
import com.so.book.admin.category.CategoryVo;
import com.so.book.common.constants.Constants;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 관리자 : 상품 관리 기능
@Slf4j
@Controller
@RequestMapping("/admin/product/*")
@RequiredArgsConstructor
public class AdProductController {
	
	private final AdProductService adProductService;
	private final AdCategoryService adCategoryService;
	// 이미지 관련 작업 기능
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@Value("${com.ezen.upload.ckeditor.path}")
	private String uploadCKPath;
	
	// 상품등록 폼 1차 카테고리 정보 출력
	@GetMapping("/pro_insert")
	public void pro_insert(Model model) {
		//List<CategoryVo> cate_list = adCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
	}
	
	// 상품등록(저장) <input type="file" name="pro_img_upload">
	@PostMapping("/pro_insert")
	public String pro_insert(ProductVo vo, MultipartFile pro_img_upload) throws Exception {
		
		//1 상품 이미지 파일업로드 작업
		String dateFolder = fileUtils.getDateFolder(); // 업로드 날짜폴더이름
		String saveFileName = fileUtils.uploadFile(uploadPath, dateFolder, pro_img_upload);
		
		vo.setPro_up_folder(dateFolder);
		vo.setPro_img(saveFileName);
		
		//2 상품정보 DB 저장
		
		adProductService.pro_insert(vo);
		
		return "redirect:/admin/product/pro_list";
	}
	
	// 상품등록시 CKEditor 사용하는 상품 설명 이미지 업로드 작업
	@PostMapping("/imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws Exception {
		
		
		// 데이터를 바이트단위로 작업하는 출력스트림의 최상위 클래스(추상)
		OutputStream out = null;
		PrintWriter printWriter = null; // 서버에서 클라이언트에게 응답정보를 보낼 때 사용
		
		// 예외처리문법
		try {
			// 1) CKeditor를 이용한 파일업로드 작업
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 업로드 파일명. 예 > abx.gif
			byte[] bytes = upload.getBytes(); // 업로드 된 파일을 바이트로 뽑아올 수 있다. 업로드하는 파일(abc.gif)을 나타내는 바이트배열
			
			// C:\\Dev\\upload\\ckeditor\\abc.gif
			String ckUploadPath = uploadCKPath + "\\" + fileName;
			
			// out 객체 생성이 되면, 해당 경로에 파일은 생성된다. 파일크기는 0byte
			out = new FileOutputStream(ckUploadPath);
			
			out.write(bytes); // out스트림 객체에 파일 byte배열을 채웠다.
			out.flush(); // out 스트림객체에 존재하고 있는 byte 배열을 빈파일에 쓰는 작업
			
			// 2) 업로드한 파일정보를 클라이언트인 CKEditor로 보내주는 작업
			// 파일정보를 클라이언트 쪽에 보낼때 사용하는 객체
			printWriter = response.getWriter();
			
			// 매핑주소
			String fileUrl = "/admin/product/display/" + fileName;
			
			// ckeditor.js 4.12에서 파일정보를 아래와 같이 작업을 하도록 가이드
			// 파일정보를 JSON 데이터 표현 형식 사용
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 채움.
			
			
			printWriter.flush();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close(); // 메모리 소멸
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if(printWriter != null) printWriter.close(); // 메모리 소멸
		}
	}
	
	@GetMapping("/display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = fileUtils.getFile(uploadCKPath, fileName);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return entity;
	}
	
	// 상품목록
	@GetMapping("/pro_list") // 데이터 보여줘야할 때 model 필요
	public void pro_list(Model model, SearchCriteria cri) throws Exception {
		
		cri.setPerPageNum(Constants.ADMIN_PRODUCT_LIST_COUNT);
		
		//1) 상품목록
		List<ProductVo> pro_list = adProductService.pro_list(cri);
		
		
		// 날짜 폴더의 \ 를 / 로 변환하는 작업
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("pro_list", pro_list); // 타임리프 사용 가능
		
		//2) 페이징
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setDisplayPageNum(Constants.ADMIN_PRODUCT_LIST_PAGE_SIZE);
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adProductService.getTotalCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	// 상품목록 이미지출력하기
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
	
	// 상품수정폼
	
	// 상품수정(변경 저장)
	
	
	
	// 상품 삭제
	
	// 상품 검색

}
