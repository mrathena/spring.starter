package com.mrathena.spring.starter.api.service.security.authentication;

import com.mrathena.common.entity.Response;

/**
 * 认证服务
 *
 * @author mrathena on 2019-07-21 22:37
 */
public interface AuthenticationService {

	/**
	 * 认证
	 *
	 * @param request .
	 * @return .
	 */
	Response<AuthenticateResDTO> authenticate(AuthenticateReqDTO request);

}
