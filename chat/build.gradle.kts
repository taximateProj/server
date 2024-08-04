dependencies {
    implementation(project(":common"))
    implementation(project(":member"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation ("org.mockito:mockito-core")
    testImplementation ("org.springframework.kafka:spring-kafka-test")
    testImplementation ("org.springframework:spring-websocket")
    testImplementation ("org.springframework:spring-messaging")
}

