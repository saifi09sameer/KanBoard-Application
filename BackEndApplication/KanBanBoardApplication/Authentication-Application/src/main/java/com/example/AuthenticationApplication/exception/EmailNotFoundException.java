package com.example.AuthenticationApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email Not Found In Token")
public class EmailNotFoundException extends Exception{

}
