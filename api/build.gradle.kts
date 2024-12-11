plugins {
    kotlin("jvm") version "1.9.21"
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Jar> {
    archiveFileName.set("Luminia-Discord-Bot-API-${project.version}.jar")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}