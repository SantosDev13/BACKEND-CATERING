package com.catering.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service; // Importante

import com.catering.backend.dto.QuoteDTO;
import com.catering.backend.entity.QuoteRequest;
import com.catering.backend.repository.QuoteRepository; // Para que no se congele

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Leemos tu correo desde la configuración para no escribirlo a mano siempre
    @Value("${spring.mail.username}")
    private String myEmail;

    public QuoteRequest createQuote(QuoteDTO dto) {
        // 1. Guardar en BD
        QuoteRequest request = new QuoteRequest();
        request.setClientName(dto.clientName());
        request.setClientEmail(dto.clientEmail());
        request.setClientPhone(dto.clientPhone());
        request.setEventDate(dto.eventDate());
        request.setEventType(dto.eventType());
        request.setGuestCount(dto.guestCount());
        request.setLocation(dto.location());
        request.setMessageOrItems(dto.itemsOfInterest());

        QuoteRequest savedRequest = quoteRepository.save(request);

        // 2. Enviar Correos (Lo hacemos en un try-catch para que si falla el correo, no falle el guardado)
        try {
            sendAdminNotification(savedRequest); // Correo para ti
            sendClientConfirmation(savedRequest); // Correo para el cliente
        } catch (Exception e) {
            System.err.println("Error enviando correos: " + e.getMessage());
        }

        return savedRequest;
    }

    // Correo A TI (Ventas)
    @Async
    private void sendAdminNotification(QuoteRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(myEmail);
        message.setTo(myEmail); // Te lo envías a ti mismo
        message.setSubject("🔔 NUEVA COTIZACIÓN: " + request.getEventType() + " - " + request.getClientName());
        
        String body = """
                NUEVA OPORTUNIDAD DE VENTA
                ------------------------------------------------
                Cliente:  %s
                Teléfono: %s
                Correo:   %s
                
                Evento:   %s
                Fecha:    %s
                Invitados:%d
                Lugar:    %s
                
                DETALLES DEL PEDIDO:
                %s
                ------------------------------------------------
                Revisar en el panel de administración.
                """.formatted(
                        request.getClientName(),
                        request.getClientPhone(),
                        request.getClientEmail(),
                        request.getEventType(),
                        request.getEventDate(),
                        request.getGuestCount(),
                        request.getLocation(),
                        request.getMessageOrItems()
                );

        message.setText(body);
        mailSender.send(message);
    }

    // Correo AL CLIENTE (Confirmación)
    @Async
    private void sendClientConfirmation(QuoteRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(myEmail);
        message.setTo(request.getClientEmail()); // Al correo que puso en el form
        message.setSubject("Hemos recibido tu solicitud - Tu Empresa Catering");
        
        String body = """
                Hola %s,
                
                Gracias por cotizar con nosotros. Hemos recibido exitosamente tu solicitud para tu evento de %s.
                
                Nuestro equipo comercial revisará la disponibilidad para la fecha %s y te contactaremos en menos de 24 horas con una propuesta detallada.
                
                Resumen de tu interés:
                %s
                
                Si tienes alguna urgencia, puedes escribirnos a nuestro WhatsApp: 987 654 321.
                
                Atentamente,
                El equipo de Tu Empresa Catering.
                """.formatted(
                        request.getClientName(),
                        request.getEventType(),
                        request.getEventDate(),
                        request.getMessageOrItems()
                );

        message.setText(body);
        mailSender.send(message);
    }
}