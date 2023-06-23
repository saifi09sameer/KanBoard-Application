package com.kanbanboard.EmployeeNotification.service;

import com.kanbanboard.EmployeeNotification.configration.NotificationDTO;
import com.kanbanboard.EmployeeNotification.domain.Notification;
import com.kanbanboard.EmployeeNotification.exception.EmployeeNotFoundException;

import java.util.List;

public interface INotificationService {
     List<String> getNotification(String email);
     boolean removeEmployeeNotification(String employeeEmail) throws EmployeeNotFoundException;
     void saveNotification(NotificationDTO notificationDTO);

}
