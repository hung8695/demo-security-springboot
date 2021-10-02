package com.hung.demo.service.email;

import com.hung.demo.model.Admin;
import com.hung.demo.model.User;
import com.hung.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    IUserService  userService;

    public void sendVerificationEmail(User user) throws MessagingException {

        String subject = "Register notification";
        String senderName = "admin";
        String mailContent = "<p>Dear "+user.getName()+",</p>";
        mailContent += "Bạn đã đăng ký thành công!";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom(senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    public String getType(String email){
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()){
            System.out.println(user.getClass().getTypeName().toString());
            return user.get().getRole().toString();
        }
        return "";
    }
}
