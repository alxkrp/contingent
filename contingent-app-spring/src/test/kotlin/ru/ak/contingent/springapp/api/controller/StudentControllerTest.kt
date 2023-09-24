package ru.ak.contingent.springapp.api.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.ak.contingent.api.models.*
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.logging.ContLoggerProvider
import ru.ak.contingent.mappers.*
import ru.ak.contingent.springapp.config.ContAppSettings

@WebFluxTest(controllers = [StudentController::class], excludeAutoConfiguration = [ReactiveSecurityAutoConfiguration::class])
internal class StudentControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var appSettings: ContAppSettings

    private val processor = mockk<ContStudentProcessor>(relaxUnitFun = true)

    @BeforeEach
    fun beforeEach() {
        every { appSettings.processor } returns processor
        every { appSettings.logger } returns ContLoggerProvider()
    }

    @Test
    fun createStudent() = testStubStudent(
        "/student/create",
        StudentCreateRequest(),
        ContContext().toTransportCreate()
    )

    @Test
    fun readStudent() = testStubStudent(
        "/student/read",
        StudentReadRequest(),
        ContContext().toTransportRead()
    )

    @Test
    fun updateStudent() = testStubStudent(
        "/student/update",
        StudentUpdateRequest(),
        ContContext().toTransportUpdate()
    )

    @Test
    fun deleteStudent() = testStubStudent(
        "/student/delete",
        StudentDeleteRequest(),
        ContContext().toTransportDelete()
    )

    @Test
    fun searchStudent() = testStubStudent(
        "/student/search",
        StudentSearchRequest(),
        ContContext().toTransportSearch()
    )

    private inline fun <Req: Any, reified Res: Any> testStubStudent(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                Assertions.assertThat(it).isEqualTo(responseObj)
            }

        coVerify { processor.exec(any()) }
    }
}
