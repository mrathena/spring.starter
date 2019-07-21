package com.mrathena.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mrathena
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Response<T> implements Serializable {

	private boolean success;
	private T result;
	private String code;
	private String message;

	private String originalCode;
	private String originalMessage;
	private String originalServerIp;

	public Response() {}

	public Response(T result) {
		this.success = true;
		this.result = result;
	}

	public Response(String code, String message) {
		this.success = false;
		this.code = code;
		this.message = message;
	}

	public Response(String code, String message,
	                String originalCode, String originalMessage, String originalServerIp) {
		this.success = false;
		this.code = code;
		this.message = message;
		this.originalCode = originalCode;
		this.originalMessage = originalMessage;
		this.originalServerIp = originalServerIp;
	}

	public boolean isSuccess() {
		return this.success;
	}

}
