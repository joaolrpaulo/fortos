val koinVersion = "2.2.2"
val log4jVersion = "1.7.29"
val apacheLog4JVersion = "2.14.0"
val kotlinxCliVersion = "0.3.1"
val jacksonVersion = "2.12.+"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"

    application
}

repositories {
    maven("https://kotlin.bintray.com/kotlinx")
    jcenter()
}

dependencies {
    // Main Dependencies
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
    implementation("org.koin:koin-core:$koinVersion")
    implementation("org.koin:koin-logger-slf4j:$koinVersion")
    implementation("org.slf4j:slf4j-log4j12:$log4jVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")


    // Test Dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.koin:koin-test:$koinVersion")
}

application {
    mainClass.set("fortos.AppKt")
}
