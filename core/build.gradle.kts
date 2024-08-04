dependencies {
    implementation(project(":common"))

    implementation("org.springframework.kafka:spring-kafka")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation ("org.mockito:mockito-core")
    testImplementation ("org.springframework.kafka:spring-kafka-test")
}

