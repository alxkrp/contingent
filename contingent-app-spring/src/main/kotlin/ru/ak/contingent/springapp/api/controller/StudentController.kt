package ru.ak.contingent.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.ak.contingent.api.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.mappers.*
import ru.ak.contingent.springapp.service.ContStudentBlockingProcessor

@RestController
@RequestMapping("/student")
class StudentController(private val processor: ContStudentBlockingProcessor) {

    @PostMapping("/create")
    fun createStudent(@RequestBody request: StudentCreateRequest): StudentCreateResponse {
        val context = ContContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("/read")
    fun readStudent(@RequestBody request: StudentReadRequest): StudentReadResponse {
        val context = ContContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @RequestMapping("/update", method = [RequestMethod.POST])
    fun updateStudent(@RequestBody request: StudentUpdateRequest): StudentUpdateResponse {
        val context = ContContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("/delete")
    fun deleteStudent(@RequestBody request: StudentDeleteRequest): StudentDeleteResponse {
        val context = ContContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("/search")
    fun searchStudent(@RequestBody request: StudentSearchRequest): StudentSearchResponse {
        val context = ContContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
