package com.kanbanboard.EmployeeNotification.controller;

import com.kanbanboard.EmployeeNotification.exception.EmailNotFoundException;
import com.kanbanboard.EmployeeNotification.exception.EmployeeNotFoundException;
import com.kanbanboard.EmployeeNotification.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Notification/")
public class NotificationController {
    INotificationService iNotificationService;

    @Autowired
    public NotificationController(INotificationService iNotificationService) {
        this.iNotificationService = iNotificationService;
    }
    //http://localhost:9933/api/Notification/getAllNotifications {Path Variables} [TOKEN]
    @GetMapping("/getAllNotifications")
    public ResponseEntity<?> getAllNotifications(HttpServletRequest request) throws EmailNotFoundException {
        String employeeEmail = (String) request.getAttribute("employeeEmail");
        if (employeeEmail!=null){
            return new ResponseEntity<>(iNotificationService.getNotification(employeeEmail), HttpStatus.OK);
        }
        throw new EmailNotFoundException();
    }
    //http://localhost:9933/api/Notification/deleteEmployee/{employeeEmail} {Path Variables}
    @DeleteMapping("/deleteEmployee/{employeeEmail}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeEmail) throws EmployeeNotFoundException {
        return new ResponseEntity<>(iNotificationService.removeEmployeeNotification(employeeEmail),HttpStatus.OK);
    }
}
