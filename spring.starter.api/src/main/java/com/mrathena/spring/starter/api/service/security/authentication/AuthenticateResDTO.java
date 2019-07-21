package com.mrathena.spring.starter.api.service.security.authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author mrathena on 2019-07-21 23:17
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AuthenticateResDTO {

	private String username;

}
