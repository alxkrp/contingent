# API

## Сущности

1. Student
2. Speciality
3. Faculty

## Описание сущности Student

1. Fio (2-100 символов) - ФИО студента
2. Sex (1 символ) - пол (М,Ж)
3. Semester (smallint) - Семестр
4. EduYear (smallint) - учебный год
5. FacultyId (GUID 16-64 символа) - идентификатор факультета
6. SpecialityId (GUID 16-64 символа) - идентификатор специальности
7. GroupNum (3-20 символов) - № группы

## Функции (эндпониты)

1. Student CRUDS
   1. create
   2. read
   3. update
   4. delete
   5. search - поиск по фильтрам

