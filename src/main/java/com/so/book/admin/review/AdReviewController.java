package com.so.book.admin.review;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/review/*")
public class AdReviewController {

	private final AdReviewService adReviewService;
}
