package ru.ak.contingent.stubs

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.*

object ContStudStubPersons {
    val STUDENT_IVAN: ContStudent
        get() = ContStudent(
            id = ContStudentId(777),
            fio = "Иванов Иван Иванович",
/*            description = "Требуется болт 100x5 с шистигранной шляпкой",
            ownerId = MkplUserId("user-1"),
            adType = MkplDealSide.DEMAND,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                MkplAdPermissionClient.READ,
                MkplAdPermissionClient.UPDATE,
                MkplAdPermissionClient.DELETE,
                MkplAdPermissionClient.MAKE_VISIBLE_PUBLIC,
                MkplAdPermissionClient.MAKE_VISIBLE_GROUP,
                MkplAdPermissionClient.MAKE_VISIBLE_OWNER,
            )*/
        )
}
