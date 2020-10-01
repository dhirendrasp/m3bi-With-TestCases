package com.m3bi.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class BonusNotFoundException extends RuntimeException {

	public BonusNotFoundException(String message) {
		super(message);
	}
}
