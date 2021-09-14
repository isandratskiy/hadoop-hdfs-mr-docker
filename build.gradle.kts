plugins {
    java
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    group = "io.sandratskyi"
    version = "0.1.1"

    dependencies {

        implementation("org.apache.spark:spark-sql_2.12:3.1.2")
        implementation("org.apache.spark:spark-core_2.12:3.1.2")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
}