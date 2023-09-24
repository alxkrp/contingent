package ru.ak.contingent.springapp

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Ordinary user not permitted to access the endpoint`() {
        this.mockMvc
            .perform(
                post("/student/create")
                    .with(httpBasic("someuser", "password"))
            )
            .andExpect(unauthenticated())
    }

    @Test
    fun `User permitted to access the endpoint`() {
        this.mockMvc
            .perform(
                post("/student/create")
                    .with(httpBasic("admin", "password"))
            )
            .andExpect(authenticated())
    }
}