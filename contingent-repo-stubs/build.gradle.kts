plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":contingent-common"))
    implementation(project(":contingent-stubs"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    api(kotlin("test-junit"))
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))

    implementation(project(":contingent-repo-tests"))
}
