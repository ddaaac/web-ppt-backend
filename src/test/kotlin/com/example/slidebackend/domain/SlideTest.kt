package com.example.slidebackend.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SlideTest {
    @Test
    fun `ppt의 내용을 변경하면 내용이 변경된다`() {
        val slide = Slide("contents")

        slide.update("updated")

        assertThat(slide.markdown).isEqualTo("updated")
    }
}