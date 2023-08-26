package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.ak.contingent.api.models.StudentResponseObject
import ru.ak.contingent.api.models.StudentSearchFilter
import ru.ak.contingent.api.models.StudentSearchRequest
import ru.ak.contingent.api.models.StudentSearchResponse
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.searchAd(search: StudentSearchFilter): List<StudentResponseObject> = searchAd(search) {
    it should haveSuccessResult
    it.students ?: listOf()
}

suspend fun <T> Client.searchAd(search: StudentSearchFilter, block: (StudentSearchResponse) -> T): T =
    withClue("searchStudent: $search") {
        val response = sendAndReceive(
            "student/search",
            StudentSearchRequest(
                requestType = "search",
                debug = debug,
                studentFilter = search,
            )
        ) as StudentSearchResponse

        response.asClue(block)
    }
