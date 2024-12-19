package com.so.book.admin.notice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdNoticeService {

	private final AdNoticeMapper adNoticeMapper;
	
	public void save(NoticeVo vo) {
		adNoticeMapper.save(vo);
	}
	
	public List<NoticeVo> listAll(SearchCriteria cri) {
		return adNoticeMapper.listAll(cri);
	}
	
	public int getTotalCount(SearchCriteria cri) {
		return adNoticeMapper.getTotalCount(cri);
	}
	
	public NoticeVo read(int ntc_bno){
		
		adNoticeMapper.viewCount(ntc_bno);
		return adNoticeMapper.read(ntc_bno);
	}
	
	public NoticeVo edit(int ntc_bno) {
		return adNoticeMapper.edit(ntc_bno);
	}
	
	public void edit_save(NoticeVo vo){
		adNoticeMapper.edit_save(vo);
	}
	
	public void delete(int ntc_bno) {
		adNoticeMapper.delete(ntc_bno);
	}
	
	void nt_sel_delete(int[] ntc_bno_arr) {
		adNoticeMapper.nt_sel_delete(ntc_bno_arr);
	}
}
