/*plugins {
    kotlin("jvm")
}

dependencies {
    //implementation(kotlin("stdlib-common"))
    implementation(kotlin("stdlib"))

    implementation(project(":contingent-common"))

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(kotlin("test-junit"))
}*/

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
//    linuxX64 {}
//    macosX64 {}
//    macosArm64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":contingent-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
