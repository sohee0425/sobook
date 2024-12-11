package com.so.book.admin.notice;

import java.util.List;

public interface AdNoticeMapper {

	public void save(NoticeVo vo) throws Exception;
	
	List<NoticeVo> listAll() throws Exception;
	
	NoticeVo read(int ntc_bno) throws Exception;
}
