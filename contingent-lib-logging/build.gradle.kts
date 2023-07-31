plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project
    val logbackEncoderVersion: String by project
    val logbackVersion: String by project
    val datetimeVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")

    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    api("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}
