package ru.ak.contingent.api

import ru.ak.contingent.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = StudentCreateRequest(
        requestId = "123",
        debug = ContingentDebug(
            mode = ContingentRequestDebugMode.STUB,
            stub = ContingentRequestDebugStubs.BAD_FIO
        ),
        student = StudentCreateObject(
            fio = "Иванов Иван Иванович",
            sex = Sex.M,
            semester = 2,
            eduYear = 2023,
            specialityId = 5,
            facultyId = 10,
            groupNum = "123-л",
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)

        assertContains(json, Regex("\"fio\":\\s*\"Иванов Иван Иванович\""))
        assertContains(json, Regex("\"sex\":\\s*\"M\""))
        assertContains(json, Regex("\"semester\":\\s*2"))
        assertContains(json, Regex("\"eduYear\":\\s*2023"))
        assertContains(json, Regex("\"specialityId\":\\s*5"))
        assertContains(json, Regex("\"facultyId\":\\s*10"))
        assertContains(json, Regex("\"groupNum\":\\s*\"123-л\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badFio\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as StudentCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiMapper.readValue(jsonString, StudentCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
