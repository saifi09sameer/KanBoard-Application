package com.kanbanboard.EmployeeNotification.repository;

import com.kanbanboard.EmployeeNotification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends MongoRepository<Notification, String> {

}
