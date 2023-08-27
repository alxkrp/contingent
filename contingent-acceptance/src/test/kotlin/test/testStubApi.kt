package ru.ak.contingent.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.ak.contingent.api.models.StudentSearchFilter
import ru.ak.contingent.api.models.StudentUpdateObject
import ru.ak.contingent.blackbox.fixture.client.Client
import ru.ak.contingent.blackbox.test.action.*

fun FunSpec.testStubApi(client: Client, prefix: String = "") {
    context("$prefix Stub") {
        test("Create Student ok") {
            client.createStudent()
        }

        test("Read Student ok") {
            val created = client.createStudent()
            client.readStudent(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Student ok") {
            val created = client.createStudent()
            client.updateStudent(created.id, created.lock, StudentUpdateObject(fio = "Иванов Иван Иванович"))
            client.readStudent(created.id) {
                it.student?.fio shouldBe "Иванов Иван Иванович"
            }
        }

        test("Delete Student ok") {
            val created = client.createStudent()
            client.deleteStudent(created.id, created.lock)
            client.readStudent(created.id)
        }

        test("Search Student ok") {
            client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Сидорович"))
            client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Петрович"))

            withClue("Search Сидоров") {
                client.searchAd(search = StudentSearchFilter(searchString = "Сидоров"))
            }

            withClue("Search Петр") {
                client.searchAd(search = StudentSearchFilter(searchString = "Петр"))
            }
        }
    }

}