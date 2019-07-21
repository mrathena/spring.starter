package com.mrathena.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrathena on 2019/6/27 19:21
 */
@RestController
@RequestMapping("index")
public class IndexController {

	@PostMapping("index")
	public Object index() {
		return new User("mrathena", 27L);
	}

}

@Getter
@Setter
@AllArgsConstructor
class User {
	private String username;
	private Long age;
}
