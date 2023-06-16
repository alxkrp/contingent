plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":contingent-api"))
    implementation(project(":contingent-common"))

    testImplementation(kotlin("test-junit"))
}