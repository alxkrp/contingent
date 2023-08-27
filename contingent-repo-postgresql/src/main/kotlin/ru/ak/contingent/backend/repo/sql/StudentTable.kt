package ru.ak.contingent.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex

object StudentTable : Table("student") {
    val id = integer("id")
    val fio = varchar("fio", 100)
    val sex = enumeration("visibility", ContStudentSex::class)
    val semester = integer("semester")
    val eduyear = integer("eduyear")
    val speciailityid = integer("speciailityid")
    val facultyid = integer("facultyid")
    val groupnum = varchar("groupnum", 30)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = ContStudent(
        id = ContStudentId(res[id].toInt()),
        fio = res[fio],
        sex = res[sex],
        semester = res[semester],
        eduYear = res[eduyear],
        specialityId = res[speciailityid],
        facultyId = res[facultyid],
        groupNum = res[groupnum],
        lock = ContStudentLock(res[lock])
    )

    fun from(res: ResultRow) = ContStudent(
        id = ContStudentId(res[id].toInt()),
        fio = res[fio],
        sex = res[sex],
        semester = res[semester],
        eduYear = res[eduyear],
        specialityId = res[speciailityid],
        facultyId = res[facultyid],
        groupNum = res[groupnum],
        lock = ContStudentLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, student: ContStudent, randomId: () -> Int, randomUuid: () -> String) {
        it[id] = student.id.takeIf { it != ContStudentId.NONE }?.asInt() ?: randomId()
        it[fio] = student.fio
        it[sex] = student.sex
        it[semester] = student.semester
        it[eduyear] = student.eduYear
        it[speciailityid] = student.specialityId
        it[facultyid] = student.facultyId
        it[groupnum] = student.groupNum
        it[lock] = student.lock.takeIf { it != ContStudentLock.NONE }?.asString() ?: randomUuid()
    }

}
