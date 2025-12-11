plugins {
    alias(libs.plugins.wisecoders.commonGradle.jdbcDriver)
}

group = "com.wisecoders.jdbc-drivers"

jdbcDriver {
    dbId = "Redis"
}

dependencies {
    testImplementation(libs.mockito.core)
}
