package ru.ak.contingent.common.repo

interface IStudRepository {
    suspend fun createStud(rq: DbStudRequest): DbStudResponse
    suspend fun readStud(rq: DbStudIdRequest): DbStudResponse
    suspend fun updateStud(rq: DbStudRequest): DbStudResponse
    suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse
    suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse
    companion object {
        val NONE = object : IStudRepository {
            override suspend fun createStud(rq: DbStudRequest): DbStudResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateStud(rq: DbStudRequest): DbStudResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
