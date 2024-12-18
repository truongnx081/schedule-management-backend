package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.EmailRequest;
import com.fpoly.backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("truongnxps27633@fpt.edu.vn");
        message.setTo("ryugyuchuancmnr@gmail.com");

        String defaultSubject = "Tiêu đề ";
        String defaultBody = "Body";

        message.setSubject(defaultSubject);
        message.setText(defaultBody);

        emailSender.send(message);
    }
}
