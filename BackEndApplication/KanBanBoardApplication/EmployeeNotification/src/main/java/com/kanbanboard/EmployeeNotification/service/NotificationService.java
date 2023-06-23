package com.kanbanboard.EmployeeNotification.service;

import com.kanbanboard.EmployeeNotification.configration.NotificationDTO;
import com.kanbanboard.EmployeeNotification.domain.Notification;
import com.kanbanboard.EmployeeNotification.exception.EmployeeNotFoundException;
import com.kanbanboard.EmployeeNotification.repository.INotificationRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    INotificationRepository iNotificationRepository;

    @Autowired
    public NotificationService(INotificationRepository iNotificationRepository) {
        this.iNotificationRepository = iNotificationRepository;
    }

    @Override
    public List<String> getNotification(String email) {
        Notification notification = iNotificationRepository.findById(email).orElse(null);
        List<String> messages = new ArrayList<>();

        if (notification != null) {
            List<JSONObject> notificationList = notification.getNotification();

            // Reverse the order of the notification list
            Collections.reverse(notificationList);

            for (JSONObject notificationData : notificationList) {
                String message = notificationData.get("message").toString();
                messages.add(message);
            }
        }

        return messages;
    }

    @Override
    public boolean removeEmployeeNotification(String employeeEmail) throws EmployeeNotFoundException {
        Optional<Notification> optionalNotification = iNotificationRepository.findById(employeeEmail);
        if (optionalNotification.isPresent()){
            iNotificationRepository.deleteById(employeeEmail);
            return true;
        }
        throw new EmployeeNotFoundException();
    }


    @RabbitListener(queues = "notificationQueue")
    @Override
    public void saveNotification(NotificationDTO notificationDTO) {
        System.out.println("This data is coming to NOTIFICATION Service: " + notificationDTO);
        String employeeEmail = notificationDTO.getEmployeeEmail();
        Optional<Notification> existingNotification = iNotificationRepository.findById(employeeEmail);

        Notification notification;
        if (existingNotification.isEmpty()) {
            notification = new Notification();
            notification.setEmployeeEmail(employeeEmail);
            notification.setNotification(new ArrayList<>());
        } else {
            notification = existingNotification.get();
            if (notification.getNotification() == null) {
                notification.setNotification(new ArrayList<>());
            }
        }

        JSONObject taskNotification = new JSONObject();
        taskNotification.put("message", notificationDTO.getJsonObject().get("message").toString());
        notification.getNotification().add(taskNotification);
        iNotificationRepository.save(notification);
    }

}


