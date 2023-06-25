package ru.ak.contingent.springapp.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.ak.contingent.springapp.service.ContStudentBlockingProcessor
import ru.ak.contingent.api.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.mappers.*
import ru.ak.contingent.springapp.api.controller.StudentController

@WebMvcTest(StudentController::class)
internal class StudentControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var processor: ContStudentBlockingProcessor

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

    private fun <Req: Any, Res: Any> testStubStudent(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}
