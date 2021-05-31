package com.example.rosebud.emailService

import com.example.rosebud.model.wrapper.EmailBodyWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/email")
class EmailController(@Autowired val emailSender: JavaMailSender) {

    @PostMapping("/sendEmail")
    fun sendEmail(@RequestBody emailBodyWrapper: EmailBodyWrapper) {
        val message =  SimpleMailMessage()
        message.setTo(emailBodyWrapper.setTo)
        message.setSubject("Estoy interesadx en trabajar con vos")
        message.setText("Hola soy ${emailBodyWrapper.username} vi la propuesta en la app de rosebud sobre...")
        emailSender.send(message)
    }
}