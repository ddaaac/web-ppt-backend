package com.example.slidebackend.application

import com.example.slidebackend.domain.Slide
import com.example.slidebackend.domain.SlideRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class SlideServiceTest {
    @Mock
    private lateinit var slideRepository: SlideRepository

    @InjectMocks
    private lateinit var slideService: SlideService

    @Test
    fun `ppt 내용이 주어지면 ppt를 생성하고 id를 반환한다`() {
        given(slideRepository.save(any(Slide::class.java))).willReturn(Slide("contents", 1L))

        val result = slideService.create("contents")

        assertThat(result).isEqualTo(1L)
    }

    @Test
    fun `ppt id가 주어지면 해당하는 ppt를 반환한다`() {
        given(slideRepository.getOne(1L)).willReturn(Slide("contents", 1L))

        val result = slideService.get(1L)

        assertThat(result).usingRecursiveComparison().isEqualTo(Slide("contents", 1L))
    }

    @Test
    fun `ppt id와 수정할 ppt 내용이 주어지면 ppt를 수정하고 id를 반환한다`() {
        given(slideRepository.getOne(1L)).willReturn(Slide("contents", 1L))

        val result = slideService.update(1L, "updated contents")

        assertThat(result).isEqualTo(1L)
    }
}