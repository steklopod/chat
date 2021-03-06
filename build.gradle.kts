import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlin = "1.4-M2"
	kotlin("jvm") version kotlin
	kotlin("plugin.spring") version kotlin
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

repositories {
	mavenCentral(); mavenLocal(); maven("https://dl.bintray.com/kotlin/kotlin-eap")
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks {
	val java = "11"
	withType<KotlinCompile> {
		kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java
	}
	test { useJUnitPlatform() }
}
