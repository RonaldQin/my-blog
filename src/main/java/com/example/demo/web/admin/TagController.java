package com.example.demo.web.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.po.Tag;
import com.example.demo.po.Type;
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
	
	@PostMapping("/tags")
	public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
		Tag exist_tag = tagService.getTagByName(tag.getName());
		if (exist_tag != null) {
			result.rejectValue("name", "nameError", "不能重复添加标签！");
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		Tag t = tagService.saveTag(tag);
		if (t == null) {
			attributes.addFlashAttribute("message", "新增失败！");
		} else {
			attributes.addFlashAttribute("message", "新增成功！");
		}
		return "redirect:/admin/tags";
	}
	
	@GetMapping("/tags/{id}/input")
	public String editInput(@PathVariable("id") Long id, Model model) {
		model.addAttribute("tag", tagService.getTag(id));
		return "admin/tags-input";
	}
	
	@PostMapping("/tags/{id}")
	public String editPost(@Valid Tag tag, BindingResult result, @PathVariable("id") Long id, RedirectAttributes attributes) {
		Tag old_tag = tagService.getTagByName(tag.getName());
		if (old_tag != null) {
			result.rejectValue("name", "nameError", "不能重复添加标签！");
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		Tag t = tagService.updateTag(id, tag);
		if (t == null) {
			attributes.addFlashAttribute("message", "更新失败！");
		} else {
			attributes.addFlashAttribute("message", "更新成功！");
		}
		return "redirect:/admin/types";
	}
	
	@GetMapping("/tags/{id}/delete")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		tagService.deleteTag(id);
		attributes.addFlashAttribute("message", "删除成功！");
		return "redirect:/admin/tags";
	}
}
