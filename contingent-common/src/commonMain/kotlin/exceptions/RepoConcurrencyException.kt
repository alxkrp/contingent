package ru.ak.contingent.common.exceptions

import ru.ak.contingent.common.models.ContStudentLock

class RepoConcurrencyException(expectedLock: ContStudentLock, actualLock: ContStudentLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
