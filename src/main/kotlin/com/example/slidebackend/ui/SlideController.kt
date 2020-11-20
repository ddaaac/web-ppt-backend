package com.example.slidebackend.ui

import com.example.slidebackend.application.SlideService
import com.example.slidebackend.domain.Slide
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/slide")
class SlideController(private val slideService: SlideService) {
    @PostMapping
    fun save(@RequestBody markdown: String): ResponseEntity<Long> {
        println(markdown)
        val id = slideService.create(markdown)
        return ResponseEntity.created(URI.create("/slide/$id"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Location")
                .build()
    }

    @PutMapping("/{id}")
    fun update(@RequestBody markdown: String, @PathVariable id: Long): ResponseEntity<Long> {
        val updated = slideService.update(id, markdown)
        return ResponseEntity.ok().location(URI.create("/slide/$updated")).build()
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<Slide> {
        val slide = slideService.get(id)
        return ResponseEntity.ok(slide)
    }
}