plugins {
    id("java-library")
    application
}

application {
    mainClass.set("com.game.Game")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
