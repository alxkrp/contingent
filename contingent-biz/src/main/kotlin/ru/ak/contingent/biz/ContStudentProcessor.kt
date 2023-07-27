package ru.ak.contingent.biz

import ru.ak.contingent.biz.groups.operation
import ru.ak.contingent.biz.groups.stubs
import ru.ak.contingent.biz.processors.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContCommand
import ru.ak.contingent.cor.rootChain

class ContStudentProcessor {
    suspend fun exec(ctx: ContContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<ContContext> {
            initStatus("Инициализация статуса")

            operation("Создание студента", ContCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadFio("Имитация ошибки валидации ФИО")
                    stubValidationBadSex("Имитация ошибки валидации пола")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Получить студента", ContCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Изменить студента", ContCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadFio("Имитация ошибки валидации ФИО")
                    stubValidationBadSex("Имитация ошибки валидации пола")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Удалить студента", ContCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Поиск студентов", ContCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

            }
        }.build()
    }
}
