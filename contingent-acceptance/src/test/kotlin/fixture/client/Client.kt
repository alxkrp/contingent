package ru.ak.contingent.blackbox.fixture.client

/**
 * Клиент к нашему приложению в докер-композе, который умеет отправлять запрос и получать ответ.
 * Способ отправки/получения зависит от приложения - rabbit, http, ws, ...
 */
interface Client {
    /**
     * @param path путь к ресурсу, имя топика и т.п. (student/create)
     * @param request тело сообщения в виде строки
     * @return тело ответа
     */
    suspend fun sendAndReceive(path: String, request: String): String
}