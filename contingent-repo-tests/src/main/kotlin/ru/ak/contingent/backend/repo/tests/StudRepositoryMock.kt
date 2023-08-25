package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.repo.*

class StudRepositoryMock(
    private val invokeCreateAd: (DbStudRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadAd: (DbStudIdRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateAd: (DbStudRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteAd: (DbStudIdRequest) -> DbStudResponse = { DbStudResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchAd: (DbStudFilterRequest) -> DbStudsResponse = { DbStudsResponse.MOCK_SUCCESS_EMPTY },
): IStudRepository {
    override suspend fun createStud(rq: DbStudRequest): DbStudResponse {
        return invokeCreateAd(rq)
    }

    override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse {
        return invokeReadAd(rq)
    }

    override suspend fun updateStud(rq: DbStudRequest): DbStudResponse {
        return invokeUpdateAd(rq)
    }

    override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse {
        return invokeDeleteAd(rq)
    }

    override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse {
        return invokeSearchAd(rq)
    }
}
