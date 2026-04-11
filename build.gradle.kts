plugins {
    id("org.springframework.boot") version "3.2.5" // (주: 현시점 최신 안정화 버전)
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24" // 1. 스프링 클래스/함수를 'open'
    kotlin("plugin.jpa") version "1.9.24" // 2. JPA @Entity를 위한 'all-open' (03.02절)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Spring Cloud BOM 설정 필수
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
    }
}


dependencies {
    testImplementation(kotlin("test"))
    // Spring Cloud Circuit Breaker 스타터 (Resilience4j 구현체 포함)
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")

    // Feign과의 연동을 위해 필요
    implementation("io.github.resilience4j:resilience4j-feign")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // 3. Spring MVC, 내장 Tomcat, JSON(Jackson)을 포함하는 핵심 스타터
    implementation("org.springframework.boot:spring-boot-starter-web")
    // 4. JPA (다음 03.02절에서 즉시 사용)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // 5. 코틀린 data class를 Jackson이 올바르게 (역) 직렬화하기 위한 필수 모듈
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // 코틀린 표준 라이브러리 및 리플렉션
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.cloud:spring-cloud-config-server")

    // 1. 스프링 부트 기본 테스트 스타터 (JUnit, Mockito, MockMvc 등 포함)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    // 2. Kotest Runner (JUnit5 위에서 동작)
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.1")
    // 3. Kotest Assertions (shouldBe 등)
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.1")
    // 4. Spring과 Kotest를 연동하기 위한 라이브러리
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    // 5. 코틀린 네이티브 Mocking 라이브러리 (Mockito 대신 MockK 사용)
    testImplementation("io.mockk:mockk:1.13.10")

    // Eureka 클라이언트 스타터
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    runtimeOnly("com.h2database:h2") // H2 드라이버 설치

    implementation("org.springframework.cloud:spring-cloud-starter-config")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}