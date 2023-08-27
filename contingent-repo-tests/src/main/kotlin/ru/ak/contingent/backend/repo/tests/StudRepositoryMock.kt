package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.repo.*

class StudRepositoryMock(
    private val invokeCreateStudent: (DbStudRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadStudent: (DbStudIdRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateStudent: (DbStudRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteStudent: (DbStudIdRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchStudent: (DbStudFilterRequest) -> DbStudsResponse = { DbStudsResponse.MOCK_SUCCESS_EMPTY },
): IStudRepository {
    override suspend fun createStud(rq: DbStudRequest): DbStudResponse {
        return invokeCreateStudent(rq)
    }

    override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse {
        return invokeReadStudent(rq)
    }

    override suspend fun updateStud(rq: DbStudRequest): DbStudResponse {
        return invokeUpdateStudent(rq)
    }

    override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse {
        return invokeDeleteStudent(rq)
    }

    override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse {
        return invokeSearchStudent(rq)
    }
}
