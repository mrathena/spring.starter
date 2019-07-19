package com.mrathena.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mrathena on 2019/5/25 18:16
 */
@Accessors(chain = true)
@Setter
@Getter
@ToString
public class BaseDO implements Serializable {

	private Long id;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;

}


