package com.m3bi.booking.exceptions;

import java.util.Date;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionResponse {
	Date timestampe;
	String message;
	String details;

	public ExceptionResponse(Date timestampe, String message, String details) {
		super();
		this.timestampe = timestampe;
		this.message = message;
		this.details = details;
	}

	public Date getTimestampe() {
		return timestampe;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestampe=" + timestampe + ", message=" + message + ", details=" + details + "]";
	}
}
