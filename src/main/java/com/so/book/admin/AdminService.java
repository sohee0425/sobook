package com.so.book.admin;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final AdminMapper adminMapper;
	
	public AdminDTO admin_ok(String adm_id) {
		return adminMapper.admin_ok(adm_id);
	}
}
