package com.mrathena.controller.security.authentication;

import com.mrathena.common.entity.Response;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateReqDTO;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateResDTO;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrathena on 2019-07-21 23:35
 */
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("authenticate")
	public Response<AuthenticateResDTO> authenticate(AuthenticateReqDTO request) {
		return authenticationService.authenticate(request);
	}

}
