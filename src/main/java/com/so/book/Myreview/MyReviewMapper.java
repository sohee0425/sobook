package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

public interface MyReviewMapper {
	
	List<Map<String, Object>> my_reviews(String mem_id);
}
