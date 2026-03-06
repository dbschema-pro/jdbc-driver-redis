plugins {
    alias(libs.plugins.wisecoders.commonGradle.jdbcDriver)
}

group = "com.wisecoders.jdbc-drivers"

jdbcDriver {
    dbId = "Redis"
}

dependencies {
    implementation(libs.wisecoders.commonLib.commonSlf4j)
    testImplementation(libs.mockito.core)
}
