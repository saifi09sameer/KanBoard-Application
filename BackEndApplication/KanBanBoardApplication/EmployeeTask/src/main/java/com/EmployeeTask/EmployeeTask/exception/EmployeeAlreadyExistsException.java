package com.EmployeeTask.EmployeeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Employee with this id already exists")
public class EmployeeAlreadyExistsException extends Exception{
}
