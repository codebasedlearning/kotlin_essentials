plugins {
    kotlin("jvm") version "2.4.20"
}

group = "dev.codebasedlearning.kotlin"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(25)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    //testImplementation(kotlin("test"))
}

//tasks.test {
//    useJUnitPlatform()
//}
