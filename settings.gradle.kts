rootProject.name = "Контингент"

//include("m1l1-hello")
include("contingent-api")
include("contingent-api-log")
include("contingent-common")
include("contingent-mappers")
include("contingent-biz")
include("contingent-stubs")

include("contingent-app-spring")

include("contingent-app-rabbit")

include("contingent-lib-cor")
include("contingent-lib-logging")

include("contingent-repo-stubs")
include("contingent-repo-tests")
include("contingent-repo-in-memory")
//include("contingent-repo-postgresql")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-spring-boot-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}
