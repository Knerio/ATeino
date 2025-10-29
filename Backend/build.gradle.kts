plugins {
    id("java")
}

group = "dario"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fazecast:jSerialComm:2.11.2")
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
