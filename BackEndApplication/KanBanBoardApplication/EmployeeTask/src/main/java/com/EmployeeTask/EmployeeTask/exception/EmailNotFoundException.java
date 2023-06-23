package com.EmployeeTask.EmployeeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Email Not Found Exception")
public class EmailNotFoundException extends Exception{
}
