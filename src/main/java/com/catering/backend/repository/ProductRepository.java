package com.catering.backend.repository;

import com.catering.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaz mágica de Spring Data.
 * Nos permite hacer consultas a la BD sin escribir SQL.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Método personalizado para filtrar por categoría
    List<Product> findByCategory(String category);
}