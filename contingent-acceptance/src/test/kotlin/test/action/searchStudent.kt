package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.ak.contingent.api.models.*
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.searchAd(search: StudentSearchFilter, mode: ContingentDebug = debug): List<StudentResponseObject> = searchAd(search, mode) {
    it should haveSuccessResult
    it.students ?: listOf()
}

suspend fun <T> Client.searchAd(search: StudentSearchFilter, mode: ContingentDebug = debug, block: (StudentSearchResponse) -> T): T =
    withClue("searchStudent: $search") {
        val response = sendAndReceive(
            "student/search",
            StudentSearchRequest(
                requestType = "search",
                debug = mode,
                studentFilter = search,
            )
        ) as StudentSearchResponse

        response.asClue(block)
    }
