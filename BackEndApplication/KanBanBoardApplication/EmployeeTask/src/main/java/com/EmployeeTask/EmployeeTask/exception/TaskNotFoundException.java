package com.EmployeeTask.EmployeeTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Task Not Found With This ID")
public class TaskNotFoundException  extends Exception{
}
