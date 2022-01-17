package com.politechnika.appuserservice.service;

import com.politechnika.appuserservice.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendStudentNotification(String email, String token) {

        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setToken(token);
        rabbitTemplate.convertAndSend("233010",notification);
    }

}
