package com.catering.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa un producto del catálogo de alquileres.
 * Se mapea a una tabla 'products' en la base de datos.
 */
@Entity
@Data // Lombok genera Getters, Setters, ToString, etc. automágicamente
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description;

    // Categoría: MOBILIARIO, MENAJE, COCINA, etc.
    private String category;

    // URL de la imagen (puede ser de Cloudinary o S3)
    private String imageUrl;
}