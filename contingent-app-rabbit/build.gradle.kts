plugins {
    application
    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.bmuschko.docker-java-application")
}

dependencies {
    val rabbitVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models common
    implementation(project(":contingent-common"))

    // api
    implementation(project(":contingent-api"))
    implementation(project(":contingent-mappers"))

    implementation(project(":contingent-biz"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test-junit"))
    testImplementation(project(":contingent-stubs"))
}



docker {
    javaApplication {

        baseImage.set("openjdk:17")
        ports.set(listOf(8080,5672))
        images.set(setOf("${project.name}:latest"))
        jvmArgs.set(listOf("-XX:+UseContainerSupport"))
    }

}
