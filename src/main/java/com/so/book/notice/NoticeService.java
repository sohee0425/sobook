package com.so.book.notice;

import org.springframework.stereotype.Service;

import com.so.book.admin.notice.NoticeVo;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapper noticeMapepr;
	
	
	public int getTotalNotice(SearchCriteria cri) {
		return noticeMapepr.getTotalNotice(cri);
	}
	
	public NoticeVo notice_read(int ntc_bno) {
		noticeMapepr.viewAdd(ntc_bno);
		return noticeMapepr.notice_read(ntc_bno);
	}
}
