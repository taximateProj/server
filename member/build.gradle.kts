dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.h2database:h2")

    testImplementation("org.springframework.security:spring-security-test")
}

tasks {
    bootJar{
        enabled = false
    }
    jar{
        enabled = true
    }
    //common module은 jar 파일로 생성되어 다른 프로젝트에 첨부
    //따라서 bootJar로 패키징할 필요가 없다.
}


