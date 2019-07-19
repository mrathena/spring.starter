package com.mrathena.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrathena on 2019/6/27 19:21
 */
@RestController
@RequestMapping("index")
public class IndexController {

	@GetMapping("index")
	public String index() {
		return "Hello World";
	}

}
