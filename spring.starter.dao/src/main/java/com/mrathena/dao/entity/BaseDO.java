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
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class BaseDO implements Serializable {

	private Long id;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;

}



