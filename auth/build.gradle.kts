dependencies {
    implementation(project(":common"))
    implementation(project(":member"))
}

tasks.named<Jar>("jar") {
    enabled = false
}