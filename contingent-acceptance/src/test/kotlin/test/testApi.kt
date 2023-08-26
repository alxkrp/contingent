package ru.ak.contingent.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import ru.ak.contingent.api.models.StudentSearchFilter
import ru.ak.contingent.api.models.StudentUpdateObject
import ru.ak.contingent.blackbox.fixture.client.Client
import ru.ak.contingent.blackbox.test.action.*

fun FunSpec.testApi(client: Client, prefix: String = "") {
    context(prefix) {
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
            client.readStudent(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                // it should haveError("not-found")
            }
        }

        test("Search Student ok") {
            val created1 = client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Сидорович"))
            val created2 = client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Петрович"))

            withClue("Search Сидоров") {
                val results = client.searchAd(search = StudentSearchFilter(searchString = "Сидоров"))
                // TODO раскомментировать, когда будет реальный реп
                // results shouldHaveSize 2
                // results shouldExist { it.fio == "Сидоров Сидор Сидорович" }
                // results shouldExist { it.fio == "Сидоров Сидор Петрович" }
            }

            withClue("Search Петр") {
                client.searchAd(search = StudentSearchFilter(searchString = "Петр"))
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({ it.fio == "Сидоров Сидор Петрович" })
            }
        }
    }

}