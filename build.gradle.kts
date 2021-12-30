val koinVersion = "3.1.4"
val koinLogger = "2.2.2"
val slf4jVersion = "1.7.32"
val log4jVersion = "2.17.1"
val apacheLog4JVersion = "2.14.0"
val kotlinxCliVersion = "0.3.4"
val jacksonVersion = "2.13.1"
val kotlinXCoroutinesVersion = "1.6.0"
val mockitoKtVersion = "2.2.0"
val junitVersion = "5.8.2"
val rabbitmqVersion = "5.14.0"
val kotlinJunitVersion = "1.5.32"

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // Main Dependencies
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinXCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    implementation("org.apache.logging.log4j:log4j:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.rabbitmq:amqp-client:$rabbitmqVersion")

    // Test Dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinJunitVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKtVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks {
    test {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<Jar> {
        manifest { attributes["Main-Class"] = "fortos.AppKt" }
        configurations["compileClasspath"].forEach { from(zipTree(it.absoluteFile)) }
    }
}

version = "0.0.1-alpha"