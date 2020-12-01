package com.example.slidebackend.ui

import com.example.slidebackend.application.SlideService
import com.example.slidebackend.domain.Slide
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.core.StringRegularExpression.matchesRegex
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@WebMvcTest(controllers = [SlideController::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class SlideControllerTest(
        private val objectMapper: ObjectMapper
) {
    @MockBean
    private lateinit var slideService: SlideService
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .build()
    }

    @Test
    fun `post 메서드는 마크다운으로 저장할 문자열이 주어지면 Location 헤더가 반환된다`() {
        val id = 1L
        given(slideService.create(anyString())).willReturn(id)

        mockMvc.post("/ppt") {
            content = "this is markdown contents..."
        }.andExpect {
            status { isCreated() }
            header { string(HttpHeaders.LOCATION, "/ppt/${id}") }
        }
    }

    @Test
    fun `put 메서드는 수정할 문자열과 ppt 번호가 주어지면 Location 헤더가 반환된다`() {
        val id = 1L
        given(slideService.update(eq(id), anyString())).willReturn(id)

        mockMvc.put("/ppt/${id}") {
            content = "this is markdown contents..."
        }.andExpect {
            status { isOk() }
            header { string(HttpHeaders.LOCATION, matchesRegex("/ppt/${id}")) }
        }
    }

    @Test
    fun `get 메서드는 ppt 번호가 주어지면 해당하는 ppt가 반환된다`() {
        val id = 1L
        val expected = Slide("this is markdown contents...", id)
        given(slideService.get(anyLong())).willReturn(expected)

        mockMvc.get("/ppt/${id}")
                .andExpect {
                    status { isOk() }
                    content { json(objectMapper.writeValueAsString(expected)) }
                }
    }
}