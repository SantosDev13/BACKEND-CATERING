package com.catering.backend.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quotes") // Asegúrate que en MySQL la tabla se llame 'quotes'
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private LocalDate eventDate;
    private String eventType;
    private Integer guestCount;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String comments;

    // RELACIÓN 1:N (Una cotización -> Muchos items)
    @OneToMany(mappedBy = "quoteRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Muestra los items al convertir a JSON
    private List<QuoteItem> items = new ArrayList<>();

    // Método auxiliar clave para guardar correctamente
    public void addItem(QuoteItem item) {
        items.add(item);
        item.setQuoteRequest(this);
    }
}