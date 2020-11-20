package com.example.slidebackend.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Slide(
        var markdown: String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
) {
    fun update(markdown: String) {
        this.markdown = markdown
    }
}