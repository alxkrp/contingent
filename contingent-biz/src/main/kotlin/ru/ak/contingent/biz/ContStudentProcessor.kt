package ru.ak.contingent.biz

import ru.ak.contingent.biz.general.initRepo
import ru.ak.contingent.biz.general.operation
import ru.ak.contingent.biz.general.prepareResult
import ru.ak.contingent.biz.general.stubs
import ru.ak.contingent.biz.processors.*
import ru.ak.contingent.biz.repo.*
import ru.ak.contingent.biz.validation.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.models.ContCommand
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.cor.chain
import ru.ak.contingent.cor.processor
import ru.ak.contingent.cor.rootChain

class ContStudentProcessor (val settings: ContCorSettings = ContCorSettings()) {
    suspend fun exec(ctx: ContContext) = BusinessChain.exec(ctx.apply { this.settings = this@ContStudentProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<ContContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание студента", ContCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadFio("Имитация ошибки валидации ФИО")
                    stubValidationBadSex("Имитация ошибки валидации пола")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    processor("Копируем поля в studValidating") { studValidating = studRequest.copy() }
                    processor("Очистка id") { studValidating.id = ContStudentId.NONE }
                    processor("Очистка ФИО") { studValidating.fio = studValidating.fio.trim() }
                    validateFioNotEmpty("Проверка, что ФИО не пусто")
                    validateFioHasContent("Проверка символов")

                    finishStudValidation("Завершение процедуры валидации")
                }

                chain {
                    title = "Логика добавления"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание студента в БД")
                }

                prepareResult("Подготовка ответа")
            }
            operation("Получить студента", ContCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    processor("Копируем поля в studValidating") { studValidating = studRequest.copy() }
                    processor("Очистка id") { studValidating.id = ContStudentId(studValidating.id.asInt()) }
                    validateIdProperlyValue("Проверка значения id")

                    finishStudValidation("Завершение процедуры валидации")
                }

                chain {
                    title = "Логика чтения"
                    repoRead("Чтение студента из БД")
                    processor {
                        title = "Подготовка ответа для Read"
                        on { state == ContState.RUNNING }
                        handle { studRepoDone = studRepoRead }
                    }
                }

                prepareResult("Подготовка ответа")
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

                validation {
                    processor("Копируем поля в studValidating") { studValidating = studRequest.copy() }
                    processor("Очистка id") { studValidating.id = ContStudentId.NONE }
                    processor("Очистка ФИО") { studValidating.fio = studValidating.fio.trim() }
                    validateIdProperlyValue("Проверка значения id")
                    validateFioNotEmpty("Проверка, что ФИО не пусто")
                    validateFioHasContent("Проверка символов")

                    finishStudValidation("Завершение процедуры валидации")
                }

                chain {
                    title = "Логика обновления"
                    repoRead("Чтение студента из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление студента в БД")
                }

                prepareResult("Подготовка ответа")
            }
            operation("Удалить студента", ContCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    processor("Копируем поля в studValidating") { studValidating = studRequest.copy() }
                    processor("Очистка id") { studValidating.id = ContStudentId(studValidating.id.asInt()) }
                    validateIdProperlyValue("Проверка значения id")

                    finishStudValidation("Завершение процедуры валидации")
                }

                chain {
                    title = "Логика удаления"
                    repoRead("Чтение студента из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление студента из БД")
                }

                prepareResult("Подготовка ответа")
            }
            operation("Поиск студентов", ContCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

                validation {
                    processor("Копируем поля в studValidating") { studFilterValidating = studFilterRequest.copy() }

                    finishStudFilterValidation("Завершение процедуры валидации")
                }

                repoSearch("Поиск объявления в БД по фильтру")

                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}
