package ru.ak.contingent.springapp

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.ak.contingent.backend.repo.sql.RepoStudSQL

@SpringBootTest
class ApplicationTests {

    @MockkBean
    private lateinit var repo: RepoStudSQL

    @Test
    fun contextLoads() {
    }
}
