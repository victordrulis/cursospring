package com.victor.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import com.victor.cursospring.domain.Pedido;

public interface EmailService {
    
    public void sendOrderConfirmationEmail(Pedido obj);
    
    public void sendEmail(SimpleMailMessage msg);
}
