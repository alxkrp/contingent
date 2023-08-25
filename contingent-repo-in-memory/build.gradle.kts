plugins {
    kotlin("jvm")
}

dependencies {
    val cache4kVersion: String by project
    val coroutinesVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":contingent-common"))
    implementation(project(":contingent-stubs"))

    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    api(kotlin("test-junit"))
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))

    implementation(project(":contingent-repo-tests"))
}
