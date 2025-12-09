package com.catering.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catering.backend.entity.QuoteRequest;

public interface QuoteRepository extends JpaRepository<QuoteRequest, Long> {
}