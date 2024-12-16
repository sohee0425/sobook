package com.so.book.admin.notice;

import java.util.List;

import com.so.book.common.utils.SearchCriteria;

public interface AdNoticeMapper {

	public void save(NoticeVo vo) throws Exception;
	
	List<NoticeVo> listAll() throws Exception;
	
	int getTotalCount(SearchCriteria cri);
	
	NoticeVo read(int ntc_bno) throws Exception;
	
	void viewCount(int ntc_bno) throws Exception;
	
	void edit(NoticeVo vo) throws Exception;
}
