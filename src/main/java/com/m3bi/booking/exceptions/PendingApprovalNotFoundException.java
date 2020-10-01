package com.m3bi.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class PendingApprovalNotFoundException extends RuntimeException {

	public PendingApprovalNotFoundException(String message) {
		super(message);
	}
}
