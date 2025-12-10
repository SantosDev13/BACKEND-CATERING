package com.catering.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catering.backend.dto.QuoteDTO;
import com.catering.backend.entity.Product;
import com.catering.backend.entity.QuoteItem;
import com.catering.backend.entity.QuoteRequest;
import com.catering.backend.repository.ProductRepository;
import com.catering.backend.repository.QuoteRepository;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;
    
    @Autowired
    private ProductRepository productRepository; 

    // NOTA: Aquí NO lleva @Autowired. Eso causaba tu error.
    @Transactional // Esto asegura que se guarden la cotización y los items juntos
    public QuoteRequest createQuote(QuoteDTO dto) {
        
        // 1. Mapear datos del DTO a la Entidad (Cabecera)
        QuoteRequest request = new QuoteRequest();
        request.setClientName(dto.clientName());
        request.setClientEmail(dto.clientEmail());
        request.setClientPhone(dto.clientPhone());
        request.setEventDate(dto.eventDate());
        request.setEventType(dto.eventType());
        request.setGuestCount(dto.guestCount());
        request.setLocation(dto.location());
        request.setComments(dto.comments());

        // 2. Procesar los Items (Si vienen productos seleccionados)
        if (dto.items() != null && !dto.items().isEmpty()) {
            dto.items().forEach(itemDto -> {
                // Buscamos el producto real en la BD para asegurarnos que existe
                Product product = productRepository.findById(itemDto.productId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + itemDto.productId()));

                // Creamos el item de la cotización
                QuoteItem newItem = new QuoteItem();
                newItem.setProduct(product);
                newItem.setQuantity(itemDto.quantity());
                
                // Usamos el método helper para vincularlos bidireccionalmente
                request.addItem(newItem);
            });
        }

        // 3. Guardar todo en cascada
        return quoteRepository.save(request);
    }
}