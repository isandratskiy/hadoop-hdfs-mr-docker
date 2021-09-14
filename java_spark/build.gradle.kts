plugins {
    java
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}