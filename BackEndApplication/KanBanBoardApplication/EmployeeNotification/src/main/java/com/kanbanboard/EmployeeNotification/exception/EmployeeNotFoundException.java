package com.kanbanboard.EmployeeNotification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email Not Found Exception")
public class EmployeeNotFoundException extends Exception{


}
