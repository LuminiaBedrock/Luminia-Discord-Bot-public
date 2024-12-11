plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java.sourceCompatibility = JavaVersion.VERSION_21

tasks.build {
    dependsOn(tasks.shadowJar)
}

allprojects {
    group = "com.mefrreex"
    description = "discordbot"
    version = "2.2.3"
}

subprojects {

    apply {
        plugin("java-library")
    }

    repositories {
        mavenLocal()
        maven("https://repo.maven.apache.org/maven2/")
    }

    dependencies {
        api("net.dv8tion:JDA:5.0.0-beta.23")
        compileOnlyApi("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }
}