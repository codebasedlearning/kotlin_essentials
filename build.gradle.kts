plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dev.codebasedlearning.kotlin"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(23)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    //testImplementation(kotlin("test"))
}

//tasks.test {
//    useJUnitPlatform()
//}
