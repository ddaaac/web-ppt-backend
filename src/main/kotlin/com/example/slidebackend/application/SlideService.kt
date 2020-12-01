package com.example.slidebackend.application

import com.example.slidebackend.domain.Slide
import com.example.slidebackend.domain.SlideRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SlideService(private val slideRepository: SlideRepository) {
    fun create(markdown: String): Long {
        return slideRepository.save(Slide(markdown))
                .id
    }

    fun get(id: Long): Slide {
        return slideRepository.getOne(id)
    }

    fun update(id: Long, markdown: String): Long {
        return slideRepository.getOne(id)
                .apply { update(markdown) }
                .id
    }
}