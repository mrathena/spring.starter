package com.mrathena.spring.starter.api.service.security.authentication;

import com.mrathena.spring.starter.api.service.BaseReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author mrathena on 2019-07-21 23:14
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AuthenticateReqDTO extends BaseReqDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
