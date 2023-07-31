plugins {
    kotlin("jvm")
}

dependencies {
    val jacksonVersion: String by project
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation(project(":contingent-api"))
    implementation(project(":contingent-api-log"))
    implementation(project(":contingent-common"))

    testImplementation(kotlin("test-junit"))
}