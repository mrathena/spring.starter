package com.mrathena.service.secutiry.authentication;

import com.mrathena.biz.secutiry.authentication.AuthenticationBiz;
import com.mrathena.common.entity.Response;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateReqDTO;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticateResDTO;
import com.mrathena.spring.starter.api.service.security.authentication.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author mrathena on 2019-07-21 23:23
 */
@Slf4j
@Component
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationBiz authenticationBiz;

	@Override
	public Response<AuthenticateResDTO> authenticate(@Valid AuthenticateReqDTO request) {
		try {
			AuthenticateResDTO authenticate = authenticationBiz.authenticate(request);
			Response<AuthenticateResDTO> response = new Response<>(authenticate);
			return response;
		} catch (Exception e) {
			return ExceptionHandler.handleBizException(e);
		}
	}

}
