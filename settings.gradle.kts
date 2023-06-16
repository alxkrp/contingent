rootProject.name = "Контингент"

//include("m1l1-hello")
include("contingent-api")

include("contingent-common")
include("contingent-mappers")


pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}