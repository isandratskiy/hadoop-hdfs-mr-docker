plugins {
    kotlin("jvm") version "1.5.10"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx.spark:kotlin-spark-api-3.0.0_2.12:1.0.0-preview1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}