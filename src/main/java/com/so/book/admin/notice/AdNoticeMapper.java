package com.so.book.admin.notice;

import java.util.List;

import com.so.book.common.utils.SearchCriteria;

public interface AdNoticeMapper {

	public void save(NoticeVo vo);
	
	List<NoticeVo> listAll(SearchCriteria cri);
	
	int getTotalCount(SearchCriteria cri);
	
	NoticeVo read(int ntc_bno);
	
	void viewCount(int ntc_bno);
	
	NoticeVo edit(int ntc_bno);
	
	void edit_save(NoticeVo vo);
}
