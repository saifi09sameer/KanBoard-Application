package com.EmployeeTask.EmployeeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Assign Employee Not Found Exception  ")
public class AssignEmployeeNotFoundException extends Exception{
}
