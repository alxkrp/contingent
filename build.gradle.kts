import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
}

allprojects {
    group = "ru.ak.contingent"
    version = "0.1-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

    subprojects {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "17"
        }
    }
}
