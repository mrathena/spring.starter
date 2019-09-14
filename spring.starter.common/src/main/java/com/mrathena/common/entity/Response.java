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
	private String description;

	private String originalCode;
	private String originalMessage;
	private String originalDescription;
	private String originalIp;

	public Response() {}

	public Response(T result) {
		this.success = true;
		this.result = result;
	}

	public Response(String code, String message, String description) {
		this.success = false;
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public Response(String code, String message) {
		this(code, message, null);
	}

	public Response(String code, String message, String description,
	                String originalCode, String originalMessage, String originalDescription, String originalIp) {
		this.success = false;
		this.code = code;
		this.message = message;
		this.description = description;
		this.originalCode = originalCode;
		this.originalMessage = originalMessage;
		this.originalDescription = originalDescription;
		this.originalIp = originalIp;
	}

	public Response(String code, String message,
	                String originalCode, String originalMessage, String originalIp) {
		this(code, message, null, originalCode, originalMessage, null, originalIp);
	}

	public boolean isSuccess() {
		return this.success;
	}

}
