plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    api(project(":api"))
    api(project(":config"))
    api("org.yaml:snakeyaml:2.3")
    api("com.github.MEFRREEX:JOOQConnector:1.0.1")
    api("org.jooq:jooq:3.19.7")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Jar> {
    archiveFileName.set("Luminia-Discord-Bot-${project.version}.jar")
    manifest {
        attributes["Main-Class"] = "com.luminia.discord.bot.Bootstrap"
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}