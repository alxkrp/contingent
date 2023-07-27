plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib-common"))
    implementation(kotlin("stdlib-jdk8"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    implementation(project(":contingent-common"))
    implementation(project(":contingent-stubs"))
    implementation(project(":contingent-lib-cor"))

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(kotlin("test-junit"))
}
