plugins {
    id("java")
}

dependencies {
    api("org.spongepowered:configurate-yaml:4.1.2")
    api("org.spongepowered:configurate-gson:4.1.2")
    api("org.spongepowered:configurate-hocon:4.1.2")
    api("com.google.guava:guava:32.1.2-jre")
}

tasks.withType<Jar> {
    archiveFileName.set("Luminia-Discord-Bot-Config-${project.version}.jar")
}