rootProject.name = "Контингент"

//include("m1l1-hello")
include("cont-personal")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }
}