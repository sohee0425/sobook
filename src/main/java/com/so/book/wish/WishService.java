package com.so.book.wish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishMapper wishMapper;
	
	public void wish_add(WishVo vo) {
		wishMapper.wish_add(vo);
	}
	
//	public List<Map<String, Object>> getLikeLIstByUserid(String mem_id) {
//		return wishMapper.getLikeLIstByUserid(mem_id);
//	}
	
	public int getLikeLIstByUserid(String mem_id) {
		return wishMapper.getLikeLIstByUserid(mem_id);
	}
	
	public List<Map<String, Object>> wish_list(String mem_id, SearchCriteria cri) {
		return wishMapper.wish_list(mem_id, cri);
	}
	
	public void wish_sel_delete(int[] pro_code, String mem_id) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("pro_code_arr", pro_code);
		map.put("mem_id", mem_id);
		
		wishMapper.wish_sel_delete(map);
	}
	
	public void wish_delete(Integer pro_code) {
		wishMapper.wish_delete(pro_code);
	}
}
