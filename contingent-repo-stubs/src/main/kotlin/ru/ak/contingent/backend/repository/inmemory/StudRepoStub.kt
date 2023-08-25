package ru.ak.contingent.backend.repository.inmemory

import ru.ak.contingent.common.repo.*
import ru.ak.contingent.stubs.ContStudentStub

class StudRepoStub() : IStudRepository {
    override suspend fun createStud(rq: DbStudRequest): DbStudResponse {
        return DbStudResponse(
            data = ContStudentStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse {
        return DbStudResponse(
            data = ContStudentStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateStud(rq: DbStudRequest): DbStudResponse {
        return DbStudResponse(
            data = ContStudentStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse {
        return DbStudResponse(
            data = ContStudentStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse {
        return DbStudsResponse(
            data = ContStudentStub.prepareSearchList(filter = "Иванов"),
            isSuccess = true,
        )
    }
}
