plugins {
    kotlin("jvm") version "1.7.22"
}

allprojects {
    plugins.apply("org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
