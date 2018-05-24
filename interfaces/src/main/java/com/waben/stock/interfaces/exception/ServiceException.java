package com.waben.stock.interfaces.exception;

public class ServiceException extends RuntimeException {

	private String type;
	private String message;

	public ServiceException(String type) {
		super(type);
		this.type = type;
	}

	public ServiceException(String type, String message) {
		super(type);
		this.type = type;
		this.message = message;
	}

	public ServiceException(String type, Throwable cause) {
		super(type, cause);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getCustomMessage() {
		return message;
	}

}
