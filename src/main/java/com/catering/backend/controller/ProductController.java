package com.catering.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catering.backend.dto.ProductRequestDTO;
import com.catering.backend.entity.Category;
import com.catering.backend.entity.Product;
import com.catering.backend.repository.CategoryRepository;
import com.catering.backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository; // Inyectamos el nuevo repo

    // LEER (GET)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // CREAR (POST) - ¡MÉTODO ACTUALIZADO!
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
        // 1. Buscamos la categoría en la BD por su nombre
        Category category = categoryRepository.findByName(dto.category())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + dto.category()));

        // 2. Creamos el producto y asignamos la relación
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setImageUrl(dto.imageUrl());
        product.setPrice(dto.price());
        product.setCategory(category); // Aquí guardamos el objeto Category real

        // 3. Guardamos
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // BORRAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}