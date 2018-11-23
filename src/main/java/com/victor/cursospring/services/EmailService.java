package com.victor.cursospring.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.victor.cursospring.domain.Cliente;
import com.victor.cursospring.domain.Pedido;

@Service
public interface EmailService {
    
    public void sendOrderConfirmationEmail(Pedido obj);
    
    public void sendEmail(SimpleMailMessage msg);
    
    void sendOrderConfirmationHtmlEmail(Pedido obj);
    
    void sendHtmlEmail(MimeMessage msg);

    public void sendNewPasswordEmail(Cliente cliente, String newPass);
}
