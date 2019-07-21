package com.mrathena.biz.secutiry.authentication;

import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateReqDTO;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019-07-21 23:23
 */
@Slf4j
@Component
public class AuthenticationBiz {

	public AuthenticateResDTO authenticate(AuthenticateReqDTO request) {
		System.out.println(request);
		boolean equals = request.getUsername().equals(request.getPassword());
		return new AuthenticateResDTO().setUsername(equals ? "true" : "false");
	}

}
