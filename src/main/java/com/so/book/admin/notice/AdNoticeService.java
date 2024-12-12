package com.so.book.admin.notice;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdNoticeService {

	private final AdNoticeMapper adNoticeMapper;
	
	public void save(NoticeVo vo) throws Exception {
		adNoticeMapper.save(vo);
	}
	
	List<NoticeVo> listAll() throws Exception {
		return adNoticeMapper.listAll();
	}
	
	public NoticeVo read(int ntc_bno) throws Exception {
		return adNoticeMapper.read(ntc_bno);
	}
}