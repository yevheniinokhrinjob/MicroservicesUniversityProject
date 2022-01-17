package com.politechnika.notification.service;

import com.politechnika.notification.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiverServiceImpl implements NotificationReceiverService {

    @Autowired
    EmailService emailService;

    @Override
    @RabbitListener(queues = "233010")
    public void listenerNotification(Notification notification) {
        emailService.sendMail(notification.getEmail(), "Activate your account", "Click this: http://localhost:8080/users/activate/" + notification.getToken());
    }

}
