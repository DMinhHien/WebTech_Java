package WebTech.WebTech.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import WebTech.WebTech.service.EmailService;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping("/Email/Thankyou")
    public ResponseEntity<Void> sendEmailThankYou(@RequestParam("email") String email, @RequestParam("cusName") String cusName){
        this.emailService.sendEmailFromTemplateSync(email, "Thank you", "thankyou", cusName);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/Email/ForgotPassword")
    public ResponseEntity<Void> sendEmailForgotPassword(@RequestParam("email") String email){
        this.emailService.sendPasswordFromTemplateSync(email, "Password Recovery", "forgotpassword");
        return ResponseEntity.ok().body(null);
    }
}
