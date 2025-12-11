package com.wisecoders.jdbc.redis

import java.sql.SQLException
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@Disabled
class RedisPreparedStatementTest : WithAssertions {
    private var connection: RedisConnection? = null

    @BeforeEach
    @Throws(Exception::class)
    fun before() {
        connection = Mockito.mock(RedisConnection::class.java)
        Mockito.`when`(connection!!.msgToServer(ArgumentMatchers.any(String::class.java))).thenReturn(
            MOCK_RESPONSE
        )
    }

    @Test
    fun executeQueryWithoutParameters() {
        try {
            val stmt = RedisPreparedStatement("get {\"id\":10}", connection!!)
            assertThat(stmt.execute()).isTrue()
            assertThat(stmt._sql).isEqualTo("get {\"id\":10}")
            assertResultSetContainsMockedResponse(stmt)
        } catch (e: Exception) {
            fail<Any?>("Failed with exception")
        }
    }

    @Test
    fun executeQueryWithIntegerParameter() {
        try {
            val stmt = RedisPreparedStatement("get {\"id\":?}", connection!!)
            stmt.setInt(1, 1000)
            assertThat(stmt.execute()).isTrue()
            assertThat(stmt._sql).isEqualTo("get {\"id\":1000}")
            assertResultSetContainsMockedResponse(stmt)
        } catch (e: Exception) {
            fail<Any?>("Failed with exception")
        }
    }

    @Test
    fun executeQueryWithNullParameter() {
        try {
            val stmt = RedisPreparedStatement("get {\"id\":?}", connection!!)
            stmt.setString(1, null)
            assertThat(stmt.execute()).isTrue()
            assertThat(stmt._sql).isEqualTo("get {\"id\":null}")
            assertResultSetContainsMockedResponse(stmt)
        } catch (e: Exception) {
            fail<Any?>("Failed with exception")
        }
    }

    @Test
    fun executeQueryWithIntAndStringParameters() {
        try {
            val stmt = RedisPreparedStatement("get {\"id\":?,type:\"?\"}", connection!!)
            stmt.setInt(1, 1000)
            stmt.setString(2, "test")
            assertThat(stmt.execute()).isTrue()
            assertThat(stmt._sql).isEqualTo("get {\"id\":1000,type:\"test\"}")
            assertResultSetContainsMockedResponse(stmt)
        } catch (e: Exception) {
            fail<Any?>("Failed with exception")
        }
    }

    @Test
    fun executeQueryWithQuoteReplacementString() {
        try {
            val stmt = RedisPreparedStatement("get {\"id\":?,type:\"?\"}", connection!!)
            stmt.setInt(1, 1000)
            stmt.setString(2, "te\$t")
            assertThat(stmt.execute()).isTrue()
            assertThat(stmt._sql).isEqualTo("get {\"id\":1000,type:\"te\$t\"}")
            assertResultSetContainsMockedResponse(stmt)
        } catch (e: Exception) {
            fail<Any?>("Failed with exception")
        }
    }

    @Throws(SQLException::class)
    private fun assertResultSetContainsMockedResponse(stmt: RedisPreparedStatement) {
        val resultSet = stmt.getResultSet()
        resultSet.next()
        assertThat(resultSet).isNotNull()
        assertThat(resultSet.getString(0)).isEqualTo(MOCK_RESPONSE)
    }

    companion object {
        const val MOCK_RESPONSE: String = "mockResult"
    }
}
