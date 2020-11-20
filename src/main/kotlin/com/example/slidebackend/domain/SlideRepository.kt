package com.example.slidebackend.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SlideRepository: JpaRepository<Slide, Long> {
}