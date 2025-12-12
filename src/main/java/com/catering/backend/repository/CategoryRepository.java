package com.catering.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catering.backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Método mágico: Spring crea el SQL automáticamente para buscar por nombre
    Optional<Category> findByName(String name);
}