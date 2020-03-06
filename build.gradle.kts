plugins {
    java
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_13
}

val developmentOnly = configurations.create("developmentOnly")

configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom (developmentOnly)
    }
    compileOnly {
        extendsFrom (configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

ext {
    set("springCloudVersion", "Hoxton.SR1")
}

sourceSets.create("unitTest")
sourceSets.create("sliceTest")
sourceSets.create("integrationTest")



configure<SourceSetContainer> {
    val main by getting
    named("unitTest") {
        compileClasspath += main.output
        runtimeClasspath += main.output
        java.srcDir("src/unittest/java")
        resources.srcDir("src/unittest/resources")

    }

    named("sliceTest") {

        compileClasspath += main.output
        runtimeClasspath += main.output
        java.srcDir("src/slicetest/java")
        resources.srcDir("src/slicetest/resources")
    }

    named("integrationTest") {

        compileClasspath += main.output
        runtimeClasspath += main.output
        java.srcDir("src/integrationtest/java")
        resources.srcDir("src/integrationtest/resources")
    }
}


dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springframework.boot:spring-boot-starter-cache")
    implementation ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
//    implementation "org.springframework.boot:spring-boot-starter-data-rest" won"t work with webflux
//    implementation "org.springframework.boot:spring-boot-starter-hateoas" won"t work with webflux
    implementation ("org.springframework.boot:spring-boot-autoconfigure")
//    implementation "org.springframework.boot:spring-boot-starter-oauth2-resource-server"
//    implementation "org.springframework.boot:spring-boot-starter-security"
    implementation ("org.springframework.boot:spring-boot-starter-webflux")
//    implementation "com.okta.spring:okta-spring-boot-starter:1.4.0"
//    implementation "org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j"
//    implementation "org.springframework.cloud:spring-cloud-starter-config"
//    implementation "org.springframework.cloud:spring-cloud-starter-gateway"
//    implementation "org.springframework.cloud:spring-cloud-starter-netflix-eureka-client"
//    implementation "org.springframework.cloud:spring-cloud-starter-zipkin"
    runtimeOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly ("org.projectlombok:lombok")
    developmentOnly ("org.springframework.boot:spring-boot-devtools")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor ("org.projectlombok:lombok")

    "unitTestImplementation" ("org.springframework.boot:spring-boot-starter-test") {
        exclude ("org.junit.vintage","junit-vintage-engine")
    }
    "unitTestImplementation" ("io.projectreactor:reactor-test")
    "unitTestImplementation" ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    "unitTestRuntimeOnly"  ("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    "unitTestRuntimeOnly"  ("org.springframework.boot:spring-boot-starter-webflux")
//    "unitTestRuntimeOnly"  ("org.springframework.boot:spring-boot-starter-validation")

    "sliceTestImplementation" ("org.springframework.boot:spring-boot-starter-test") {
        exclude ("org.junit.vintage","junit-vintage-engine")
    }
    "sliceTestImplementation" ("io.projectreactor:reactor-test")
    "sliceTestImplementation" ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    "sliceTestImplementation"  ("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    "sliceTestRuntimeOnly"  ("org.springframework.boot:spring-boot-starter-webflux")
    //    sliceTestImplementation "org.springframework.security:spring-security-test"

    "integrationTestImplementation" ("org.springframework.boot:spring-boot-starter-test") {
        exclude ("org.junit.vintage","junit-vintage-engine")
    }
    "integrationTestImplementation" ("io.projectreactor:reactor-test")
    "integrationTestImplementation" ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    "integrationTestImplementation"  ("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    "integrationTestImplementation"  ("org.springframework.boot:spring-boot-starter-webflux")
    "integrationTestImplementation"  ("org.springframework.boot:spring-boot-starter-validation")
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:${ext.get("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
