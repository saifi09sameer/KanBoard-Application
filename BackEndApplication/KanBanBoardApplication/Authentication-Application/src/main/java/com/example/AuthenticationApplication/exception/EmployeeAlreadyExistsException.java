package com.example.AuthenticationApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee with this id already exists")
public class EmployeeAlreadyExistsException extends Exception {

}
