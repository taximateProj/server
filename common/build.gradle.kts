dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")
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
