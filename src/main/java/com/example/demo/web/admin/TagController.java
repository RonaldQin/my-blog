package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.po.Tag;
import com.example.demo.service.TagService;

@Controller
@RequestMapping("/admin")
public class TagController {
	@Autowired
	private TagService tagService;
	
	@GetMapping("/tags")
	public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		model.addAttribute("page", tagService.listTag(pageable));
		return "admin/tags";
	}
	
	@GetMapping("/tags/input")
	public String input(Model model) {
		model.addAttribute("tag", new Tag());
		return "admin/tags-input";
	}
	
	
}
