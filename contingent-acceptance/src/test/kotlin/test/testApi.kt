package ru.ak.contingent.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import ru.ak.contingent.api.models.StudentSearchFilter
import ru.ak.contingent.api.models.StudentUpdateObject
import ru.ak.contingent.blackbox.fixture.client.Client
import ru.ak.contingent.blackbox.test.action.*

fun FunSpec.testApi(client: Client, prefix: String = "") {
    context("$prefix Prod") {
        test("Create Student ok") {
            client.createStudent(mode = prod)
        }

        test("Read Student ok") {
            val created = client.createStudent(mode = prod)
            client.readStudent(created.id, mode = prod).asClue {
                it shouldBe created
            }
        }

        test("Update Student ok") {
            val created = client.createStudent(mode = prod)
            client.updateStudent(created.id, created.lock, StudentUpdateObject(fio = "Петров Иван Иванович"), mode = prod)
            client.readStudent(created.id, mode = prod) {
                it.student?.fio shouldBe "Петров Иван Иванович"
            }
        }

        test("Delete Student ok") {
            val created = client.createStudent(mode = prod)
            client.deleteStudent(created.id, created.lock, mode = prod)
            client.readStudent(created.id, mode = prod) {
                it should haveError("not-found")
            }
        }

        test("Search Student ok") {
            client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Сидорович"), mode = prod)
            client.createStudent(someCreateStudent.copy(fio = "Сидоров Сидор Петрович"), mode = prod)

            withClue("Search Сидоров") {
                val results = client.searchAd(search = StudentSearchFilter(searchString = "Сидоров"), mode = prod)
                results shouldHaveSize 2
                results shouldExist { it.fio == "Сидоров Сидор Сидорович" }
                results shouldExist { it.fio == "Сидоров Сидор Петрович" }
            }

            withClue("Search Петр") {
                client.searchAd(search = StudentSearchFilter(searchString = "Петр"), mode = prod)
                .shouldExistInOrder({ it.fio == "Сидоров Сидор Петрович" })
            }
        }
    }

}