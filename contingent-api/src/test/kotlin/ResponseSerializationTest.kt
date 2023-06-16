package ru.ak.contingent.api

import ru.ak.contingent.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = StudentCreateResponse(
        requestId = "123",
        student = StudentResponseObject(
            fio = "Иванов Иван Иванович",
            sex = Sex.M,
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(response)

        assertContains(json, Regex("\"fio\":\\s*\"Иванов Иван Иванович\""))
        assertContains(json, Regex("\"sex\":\\s*\"M\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, IResponse::class.java) as StudentCreateResponse

        assertEquals(response, obj)
    }
}
