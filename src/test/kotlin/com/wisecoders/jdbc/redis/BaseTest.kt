package com.wisecoders.jdbc.redis

import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import org.assertj.core.api.Assertions
import org.assertj.core.api.WithAssertions

abstract class BaseTest : WithAssertions {

    fun assertNotSupported(block: () -> Unit): SQLFeatureNotSupportedException {
        return assertThrows(SQLFeatureNotSupportedException::class.java, block)
    }

    fun assertSQLException(block: () -> Unit): SQLException {
        return assertThrows(SQLException::class.java, block)
    }

    companion object {
        protected fun <X : Throwable> assertThrows(
            exceptionClass: Class<X>,
            block: () -> Unit
        ): X {
            try {
                block()
            } catch (ex: Throwable) {
                if (exceptionClass.isInstance(ex)) {
                    return exceptionClass.cast(ex)
                }
            }
            Assertions.fail<Any>("Failed to throw expected exception: ${exceptionClass.name}")
            throw AssertionError("Unreachable") // makes compiler happy
        }
    }
}
