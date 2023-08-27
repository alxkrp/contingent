package ru.ak.contingent.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.ak.contingent.api.models.*
import ru.ak.contingent.springapp.config.ContAppSettings

@RestController
@RequestMapping("/student")
class StudentController(private val appSettings: ContAppSettings) {

    private val logger by lazy { appSettings.logger.logger(StudentController::class) }

    @PostMapping("/create")
    suspend fun createStudent(@RequestBody request: StudentCreateRequest): StudentCreateResponse =
        process(appSettings, request, logger, "student-create")

    @PostMapping("/read")
    suspend fun readStudent(@RequestBody request: StudentReadRequest): StudentReadResponse =
        process(appSettings, request, logger, "student-read")

    @PostMapping("/update")
    suspend fun updateStudent(@RequestBody request: StudentUpdateRequest): StudentUpdateResponse =
        process(appSettings, request, logger, "student-update")

    @PostMapping("/delete")
    suspend fun deleteStudent(@RequestBody request: StudentDeleteRequest): StudentDeleteResponse =
        process(appSettings, request, logger, "student-delete")

    @PostMapping("/search")
    suspend fun searchStudent(@RequestBody request: StudentSearchRequest): StudentSearchResponse =
        process(appSettings, request, logger, "student-search")
}
