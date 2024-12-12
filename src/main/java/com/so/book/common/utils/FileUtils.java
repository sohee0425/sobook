package com.so.book.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

// 파일 업로드와 관련된 작업

@Component // bean생성
public class FileUtils {

	// 기본 : 파일 업로드 시 날짜폴더생성하여 업로드한다.
	
	// 날짜 폴더이름 형식 만들기
	public String getDateFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		
		Date date = new Date(); // 현재날짜와 시간
		String str = sdf.format(date); 
		
		// File.separator : 현재 운영체제의 파일경로 구분자
		
		return str.replace("-", File.separator); // 날짜폴더생성
	}
	
	// File 클래스 : 파일 또는 폴더작업 할 때 사용
	// 업로드되는 파일구분(이미지파일 또는 일반파일 구분)
	public boolean checkImageType(File saveFile) {
		boolean isImageType = false;
		
		try {
			// 업로드 되는 파일의 MIME Type 정보
			// text/html, text/plain, image/jaeg, image/jpg, image/png ....
			String contentType = Files.probeContentType(saveFile.toPath());
			// contentType 변수 안에 내용이 image 라는 문자열로 되어있으면 true 아니면 false
			isImageType = contentType.startsWith("image");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return isImageType;	
	}
	
	// 파일 업로드 작업 및 실제 업로드에 사용한 파일명을 리턴
	public String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
		
		String realUploadFileName = "";
		
		// 업로드 할 폴더 file객체
		File file = new File(uploadFolder, dateFolder); // 예>"C:\\Dev\\upload\\pds" "2024\11\18"
		
		// "C:\\Dev\\upload\\pds\\2024\\11\\18" 폴더
		if(file.exists() == false) {
			file.mkdirs(); // 하위폴더까지 폴더생성  "C:\\Dev\\upload\\pds\\2024\\11\\18" 
		}
		
		// 클라이언트에서 보낸 파일명
		String clientFileName = uploadFile.getOriginalFilename(); // abc.gif
		UUID uuid = UUID.randomUUID();
		
		realUploadFileName = uuid.toString() + "_" + clientFileName;
		
		// 파일 업로드 작업
		try {
			File saveFile = new File(file, realUploadFileName);
			uploadFile.transferTo(saveFile); // 파일복사
			
			// 썸네일 작업을 위한 이미지 파일체크
			if(checkImageType(saveFile)) { // 이미지파일
				
				// 썸네일작업을 위한 File 객체
				File thumnailFile = new File(file, "s_" + realUploadFileName);
				
				BufferedImage bo_img = ImageIO.read(saveFile);
				double ratio = 3;
				int width = (int) (bo_img.getWidth() / ratio);
				int height = (int) (bo_img.getHeight() / ratio);
				
				Thumbnails.of(saveFile)
							.size(width, height)
							.toFile(thumnailFile);
		
			}
	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// 실제 업로드한 파일명을 리턴
		return realUploadFileName;
	}
	
	// 업로드된 파일이 프로젝트 내부에 존재하는 것이 아니라, 외부인 일반폴더에 있을 때 보안이슈가 있어서 일반폴더에 있는 파일들을 바이트배열로 읽어서 클라이언트로 보낸다.
	// 프로젝트 내부에 이미지파일. <img src="abc.gif"> 로 작업
	// 프로젝트 외부(일반폴더) 이미지파일. <img src="매핑주소"> 로 작업
	// uploadPath > C:\\Dev\\upload\\pds
	// fileName > 2024\11\19/s_9532cb0e-5028-4507-8c49-91f43224a210_luke-miller-RqtyeqBeFCg-unsplash.jpg
	public ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
	 
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName);
		
		if(!file.exists()) {
			return entity;
		}
		
		// 서버에서 파일을 클라이언트로 보낼때 파일에 대한 MIME TYPE정보를 헤더에 추가하는 작업
		HttpHeaders headers = new HttpHeaders();
		// Files.probeContentType(file.toPath())) : MIME TYPE 예 > image/jpeg, image/gif
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	// uploadPath : C:\\Dev\\upload\\pds
	// dateFolderName : 날짜 폴더 2024\11\19
	// fileName : 파일명 s_9532cb0e-5028-4507-8c49-91f43224a210_luke-miller-RqtyeqBeFCg-unsplash.jpg
	// type : image 체크
	public void delete(String uploadPath, String dateFolderName, String fileName, String type) {
		
		// 아래코드를 실행하는 운영체제에 따라서 구분자를 사용하기 위한 목적
		File file1 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName).replace('\\', File.separatorChar));
		if(file1.exists()) file1.delete();
		
		if(type.equals("image")) { // true : 원본 이미지파일 삭제
			// s_9532cb0e-5028-4507-8c49-91f43224a210_luke-miller-RqtyeqBeFCg-unsplash.jpg  /	fileName.substring(2)) : s_ 제외한 원본 파일 삭제
			File file2 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName.substring(2)).replace('\\', File.separatorChar));
			if(file2.exists()) file2.delete();
		}
		
	}
	
	
	
}
