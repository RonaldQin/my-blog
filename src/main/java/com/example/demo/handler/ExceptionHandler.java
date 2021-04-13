package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class ExceptionHandler {
	
	/* 统一拦截异常做异常处理。 */
	@org.springframework.web.bind.annotation.ExceptionHandler
	public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
		log.error("Request URL: {}, Exception: {}", request.getRequestURL(), e);
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", request.getRequestURL());
		mv.addObject("exception", e);
		mv.setViewName("error/error");
		return mv;
	}
}
