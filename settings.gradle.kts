plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Luminia-Discord-Bot"

include("api")
include("config")
include("bot")
