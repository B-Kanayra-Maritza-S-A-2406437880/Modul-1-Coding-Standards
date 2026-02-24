plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.sonarqube") version "4.4.1.3373"
}


group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
description = "eshop"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}
val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "6.1.0"
val junitJupiterVersion = "5.9.1"

dependencies {
	// Implementation
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Compile only
	compileOnly("org.projectlombok:lombok")

	// Development only
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Annotation processors
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	// Test implementation
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.seleniumhq.selenium:selenium-java:${seleniumJavaVersion}")
	testImplementation("io.github.bonigarcia:selenium-jupiter:${seleniumJupiterVersion}")
	testImplementation("io.github.bonigarcia:webdrivermanager:${webdrivermanagerVersion}")
	testImplementation("org.junit.jupiter:junit-jupiter:${junitJupiterVersion}")

	// Test runtime only
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
	jvmArgs("-XX:+EnableDynamicAgentLoading", "-Xshare:off")
	filter {
		excludeTestsMatching("*FunctionalTest")
	}
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports{
		xml.required.set(true)
		html.required.set(true)
	}
}

tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"

	filter {
		excludeTestsMatching("*FunctionalTest")
	}
}

tasks.register<Test>("functionalTest") {
	description = "Runs functional tests."
	group = "verification"

	filter {
		includeTestsMatching("*FunctionalTest")
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}

sonar {
	properties {
		property("sonar.projectKey", "B-Kanayra-Maritza-S-A-2406437880_Modul-1-Coding-Standards")
		property("sonar.organization", "b-kanayra-maritza-s-a-2406437880")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

