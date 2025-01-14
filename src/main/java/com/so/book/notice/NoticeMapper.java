package com.so.book.notice;

import com.so.book.admin.notice.NoticeVo;
import com.so.book.common.utils.SearchCriteria;

public interface NoticeMapper {

	int getTotalNotice(SearchCriteria cri);
	
	NoticeVo notice_read(int ntc_bno);
	
	void viewAdd(int ntc_bno);
}
