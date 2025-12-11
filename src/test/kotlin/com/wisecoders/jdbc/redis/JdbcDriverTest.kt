package com.wisecoders.jdbc.redis

import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class JdbcDriverTest : BaseTest(), WithAssertions {
    @Test
    @Throws(Exception::class)
    fun testGetDriverForUrl() {
        val d = DriverManager.getDriver("jdbc:redis:")
        assertThat<Driver?>(d).isInstanceOf(JdbcDriver::class.java)
    }

    //@Disabled("failing test")
    @Test
    @Throws(Exception::class)
    fun testConnect() {
        assertThat<Connection?>(DriverManager.getConnection("jdbc:redis:///")).isNotNull()
        assertThat<Connection?>(DriverManager.getConnection("jdbc:redis://localhost")).isNotNull()
        assertThat<Connection?>(DriverManager.getConnection("jdbc:redis://localhost:6379")).isNotNull()
        assertThat<Connection?>(DriverManager.getConnection("jdbc:redis://localhost:6379/0")).isNotNull()
        assertThat<Connection?>(DriverManager.getConnection("jdbc:redis://localhost/0")).isNotNull()
        assertThatThrownBy(ThrowableAssert.ThrowingCallable { DriverManager.getConnection("jdbc:redis://localhost/my_db") })
            .isInstanceOf(SQLException::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun testInvalidUrl() {
        assertThatThrownBy(ThrowableAssert.ThrowingCallable { JdbcDriver().connect("jdbc:invalid", Properties()) })
            .isInstanceOf(SQLException::class.java)
    }

    @Test
    fun testGetMajorVersion() {
        assertThat(JdbcDriver().majorVersion).isEqualTo(0)
    }

    @Test
    fun testGetMinorVersion() {
        assertThat(JdbcDriver().minorVersion).isGreaterThanOrEqualTo(0)
    }

    @Test
    fun jdbcCompliant() {
        assertThat(JdbcDriver().jdbcCompliant()).isFalse()
    }


    @Test
    @Throws(Exception::class)
    fun validateUnimplementedMethods() {
        assertNotSupported{ JdbcDriver().parentLogger }
    }
}
