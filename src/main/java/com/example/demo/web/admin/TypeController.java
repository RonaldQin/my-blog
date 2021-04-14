package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
	@Autowired
	private TypeService typeService;

	@GetMapping("/types")
	public String types(
			@PageableDefault(size = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		model.addAttribute("page", typeService.listType(pageable));
		return "admin/types";
	}
}
