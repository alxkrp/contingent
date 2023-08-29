package ru.ak.contingent.blackbox.fixture.db

interface DbClearer {
    /**
     * Очищает БД (возвращает ее к начальному состоянию)
     */
    fun clear()

    fun close()
}