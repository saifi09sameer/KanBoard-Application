package com.EmployeeTask.EmployeeTask.proxy;

import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "NotificationService", url = "http://employee-notification-service:9933/")
public interface INotificationProxy {
    @DeleteMapping("/api/Notification/deleteEmployee/{employeeEmail}")
    ResponseEntity deleteEmployee(@PathVariable String employeeEmail) throws EmployeeNotFoundException;
}
