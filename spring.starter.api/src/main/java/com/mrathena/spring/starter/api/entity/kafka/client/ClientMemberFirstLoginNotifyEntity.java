package com.mrathena.spring.starter.api.entity.kafka.client;

import com.mrathena.spring.starter.api.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 客户端用户首次登录通知
 *
 * @author mrathena on 2019-07-21 22:43
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ClientMemberFirstLoginNotifyEntity extends BaseEntity {

	@NotBlank
	@Length(min = 11, max = 11)
	private String phone;

}
