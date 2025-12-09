package com.catering.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que almacena la solicitud de cotización del cliente.
 */
@Entity
@Data
@Table(name = "quote_requests")
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos del Cliente
    private String clientName;
    private String clientEmail;
    private String clientPhone;

    // Datos del Evento
    private LocalDate eventDate;
    private String eventType; // Boda, Corporativo, etc.
    private Integer guestCount;
    private String location; // Distrito

    // Detalles: Lista de items o servicios solicitados en texto
    // Para simplificar esta fase, guardaremos los items pedidos como un texto largo
    // Ejemplo: "50 Sillas Tiffany, 2 Mesas Redondas, Servicio de Mozos"
    @Column(columnDefinition = "TEXT")
    private String messageOrItems;
}