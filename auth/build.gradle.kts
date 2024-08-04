dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation ("io.jsonwebtoken:jjwt-api")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson")
}

tasks.named<Jar>("jar") {
    enabled = false
}