package com.mrathena.spring.starter.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author mrathena on 2019-07-21 22:44
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class BaseEntity implements Serializable {

	@NotBlank
	@Length(min = 32, max = 32)
	private String uniqueNo;

}
