package com.so.book.notice;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapepr noticeMapepr;
	
	public int getTotalNotice(int ntc_bno) {
		return noticeMapepr.getTotalNotice(ntc_bno);
	}
	
	
}
