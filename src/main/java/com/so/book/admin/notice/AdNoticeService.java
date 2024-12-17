package com.so.book.admin.notice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdNoticeService {

	private final AdNoticeMapper adNoticeMapper;
	
	public void save(NoticeVo vo) {
		adNoticeMapper.save(vo);
	}
	
	List<NoticeVo> listAll(SearchCriteria cri) {
		return adNoticeMapper.listAll(cri);
	}
	
	public int getTotalCount(SearchCriteria cri) {
		return adNoticeMapper.getTotalCount(cri);
	}
	
	public NoticeVo read(int ntc_bno){
		
		adNoticeMapper.viewCount(ntc_bno);
		return adNoticeMapper.read(ntc_bno);
	}
	
	public void edit(NoticeVo vo){
		
		adNoticeMapper.edit(vo);
	}
}
