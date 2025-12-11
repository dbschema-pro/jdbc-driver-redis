package com.wisecoders.jdbc.redis

import java.io.InputStream
import java.io.Reader
import java.sql.Blob
import java.sql.Clob
import java.sql.NClob
import java.sql.ResultSet
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class RedisResultSetTest : BaseTest(), WithAssertions {
    @Test
    @Throws(Exception::class)
    fun integerMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("5", "1.1", "c", null))
        rs.next()
        assertThat(rs.getInt(0)).isEqualTo(5)
        assertThat(rs.getInt("")).isEqualTo(5)
        rs.next()
        assertSQLException { rs.getInt(0) }
        assertSQLException { rs.getInt("") }
        rs.next()
        assertSQLException { rs.getInt(0) }
        assertSQLException { rs.getInt("") }
        rs.next()
        assertThat(rs.getInt(0)).isEqualTo(0)
        assertThat(rs.getInt("")).isEqualTo(0)
        rs.close()
    }

    @Test
    @Throws(Exception::class)
    fun longMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("5", "1.1", "c", null))
        rs.next()
        assertThat(rs.getLong(0)).isEqualTo(5)
        assertThat(rs.getLong("")).isEqualTo(5)
        rs.next()
        assertSQLException { rs.getLong(0) }
        assertSQLException { rs.getLong("") }
        rs.next()
        assertSQLException { rs.getLong(0) }
        assertSQLException { rs.getLong("") }
        rs.next()
        assertThat(rs.getLong(0)).isEqualTo(0)
        assertThat(rs.getLong("")).isEqualTo(0)
        rs.close()
    }

    @Test
    @Throws(Exception::class)
    fun shortMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("5", "1.1", "c", null))
        rs.next()
        assertThat(rs.getShort(0)).isEqualTo(5.toShort())
        assertThat(rs.getShort("")).isEqualTo(5.toShort())
        rs.next()
        assertSQLException { rs.getShort(0) }
        assertSQLException { rs.getShort("") }
        rs.next()
        assertSQLException { rs.getShort(0) }
        assertSQLException { rs.getShort("") }
        rs.next()
        assertThat(rs.getShort(0)).isEqualTo(0.toShort())
        assertThat(rs.getShort("")).isEqualTo(0.toShort())
        rs.close()
    }

    @Test
    @Throws(Exception::class)
    fun doubleMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("5", "1.1", "c", null))
        rs.next()
        assertThat(rs.getDouble(0)).isCloseTo(5.0, within(0.001))
        assertThat(rs.getDouble("")).isCloseTo(5.0, within(0.001))
        rs.next()
        assertThat(rs.getDouble(0)).isCloseTo(1.1, within(0.001))
        assertThat(rs.getDouble("")).isCloseTo(1.1, within(0.001))
        rs.next()
        assertSQLException { rs.getDouble(0) }
        assertSQLException { rs.getDouble("") }
        rs.next()
        assertThat(rs.getDouble(0)).isCloseTo(0.0, within(0.001))
        assertThat(rs.getDouble("")).isCloseTo(0.0, within(0.001))

        rs.close()
    }

    @Test
    @Throws(Exception::class)
    fun floatMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("5", "1.1", "c", null))
        rs.next()
        assertThat(rs.getFloat(0)).isCloseTo(5.0f, within(0.001f))
        assertThat(rs.getFloat("")).isCloseTo(5.0f, within(0.001f))
        rs.next()
        assertThat(rs.getFloat(0)).isCloseTo(1.1f, within(0.001f))
        assertThat(rs.getFloat("")).isCloseTo(1.1f, within(0.001f))
        rs.next()
        assertSQLException { rs.getFloat(0) }
        assertSQLException { rs.getFloat("") }
        rs.next()
        assertThat(rs.getFloat(0)).isCloseTo(0.0f, within(0.001f))
        assertThat(rs.getFloat("")).isCloseTo(0.0f, within(0.001f))

        rs.close()
    }

    @Test
    @Throws(Exception::class)
    fun positionalMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>("a", "b", "c"))

        assertThat(rs.getFetchDirection()).isEqualTo(ResultSet.TYPE_SCROLL_INSENSITIVE)
        assertThat(rs.getFetchSize()).isEqualTo(3)
        assertThat(rs.getRow()).isEqualTo(0)

        assertThat(rs.isBeforeFirst()).isTrue()
        assertThat(rs.isFirst()).isFalse()
        assertThat(rs.isLast()).isFalse()
        assertThat(rs.isAfterLast()).isFalse()

        assertThat(rs.first()).isTrue()
        assertThat(rs.getString(0)).isEqualTo("a")
        assertThat(rs.isBeforeFirst()).isFalse()
        assertThat(rs.isFirst()).isTrue()
        assertThat(rs.isLast()).isFalse()
        assertThat(rs.isAfterLast()).isFalse()

        assertThat(rs.last()).isTrue()
        assertThat(rs.getString(0)).isEqualTo("c")
        assertThat(rs.isBeforeFirst()).isFalse()
        assertThat(rs.isFirst()).isFalse()
        assertThat(rs.isLast()).isTrue()
        assertThat(rs.isAfterLast()).isFalse()

        rs.afterLast()
        assertThat(rs.isBeforeFirst()).isFalse()
        assertThat(rs.isFirst()).isFalse()
        assertThat(rs.isLast()).isFalse()
        assertThat(rs.isAfterLast()).isTrue()
        assertSQLException { rs.getString(0) }

        rs.beforeFirst()
        assertThat(rs.isBeforeFirst()).isTrue()
        assertThat(rs.isFirst()).isFalse()
        assertThat(rs.isLast()).isFalse()
        assertThat(rs.isAfterLast()).isFalse()
        assertSQLException { rs.getString(0) }

        rs.close()
    }


    @Test
    @Throws(Exception::class)
    fun validateUnimplementedMethods() {
        val rs: ResultSet = RedisResultSet(arrayOf<String?>())

        assertNotSupported { rs.cancelRowUpdates() }
        assertNotSupported { rs.insertRow() }
        assertNotSupported { rs.deleteRow() }
        assertNotSupported { rs.getArray(0) }
        assertNotSupported { rs.getArray("") }
        assertNotSupported { rs.getDate(0) }
        assertNotSupported { rs.getDate("") }
        assertNotSupported { rs.getDate(0, null) }
        assertNotSupported { rs.getDate("", null) }
        assertNotSupported { rs.getTime(0) }
        assertNotSupported { rs.getTimestamp(0) }
        assertNotSupported { rs.getTime("") }
        assertNotSupported { rs.getTimestamp("") }
        assertNotSupported { rs.getTime(0, null) }
        assertNotSupported { rs.getTime("", null) }
        assertNotSupported { rs.getTimestamp(0, null) }
        assertNotSupported { rs.getTimestamp("", null) }
        assertNotSupported { rs.moveToInsertRow() }
        assertNotSupported { rs.updateNull(0) }
        assertNotSupported { rs.updateBoolean(0, true) }
        assertNotSupported { rs.updateByte(0, 0.toByte()) }
        assertNotSupported { rs.updateShort(0, 0.toShort()) }
        assertNotSupported { rs.updateInt(0, 0) }
        assertNotSupported { rs.updateLong(0, 0) }
        assertNotSupported { rs.updateFloat(0, 0f) }
        assertNotSupported { rs.updateDouble(0, 0.0) }
        assertNotSupported { rs.updateBigDecimal(0, null) }
        assertNotSupported { rs.updateString(0, "") }
        assertNotSupported { rs.updateBytes(0, null) }
        assertNotSupported { rs.updateDate(0, null) }
        assertNotSupported { rs.updateTime(0, null) }
        assertNotSupported { rs.updateTimestamp(0, null) }
        assertNotSupported { rs.updateAsciiStream(0, null, 0) }
        assertNotSupported { rs.updateBinaryStream(0, null, 0) }
        assertNotSupported { rs.updateCharacterStream(0, null, 0) }
        assertNotSupported { rs.updateObject(0, null, 0) }
        assertNotSupported { rs.updateObject(0, null) }
        assertNotSupported { rs.updateNull("") }
        assertNotSupported { rs.updateBoolean("", true) }
        assertNotSupported { rs.updateByte("", 0.toByte()) }
        assertNotSupported { rs.updateShort("", 0.toShort()) }
        assertNotSupported { rs.updateInt("", 0) }
        assertNotSupported { rs.updateLong("", 0) }
        assertNotSupported { rs.updateFloat("", 0f) }
        assertNotSupported { rs.updateDouble("", 0.0) }
        assertNotSupported { rs.updateBigDecimal("", null) }
        assertNotSupported { rs.updateString("", "") }
        assertNotSupported { rs.updateBytes("", null) }
        assertNotSupported { rs.updateDate("", null) }
        assertNotSupported { rs.updateTime("", null) }
        assertNotSupported { rs.updateTimestamp("", null) }
        assertNotSupported { rs.updateAsciiStream("", null, 0) }
        assertNotSupported { rs.updateBinaryStream("", null, 0) }
        assertNotSupported { rs.updateCharacterStream("", null, 0) }
        assertNotSupported { rs.updateObject("", null, 0) }
        assertNotSupported { rs.updateObject("", null) }
        assertNotSupported { rs.updateRow() }
        assertNotSupported { rs.updateRef(0, null) }
        assertNotSupported { rs.updateRef("", null) }
        assertNotSupported { rs.updateBlob(0, null as Blob?) }
        assertNotSupported { rs.updateBlob("", null as Blob?) }
        assertNotSupported { rs.updateClob(0, null as Clob?) }
        assertNotSupported { rs.updateClob("", null as Clob?) }
        assertNotSupported { rs.updateArray(0, null) }
        assertNotSupported { rs.updateArray("", null) }
        assertNotSupported { rs.updateRowId(0, null) }
        assertNotSupported { rs.updateRowId("", null) }
        assertNotSupported { rs.updateNString(0, "") }
        assertNotSupported { rs.updateNString("", "") }
        assertNotSupported { rs.updateNClob(0, null as NClob?) }
        assertNotSupported { rs.updateNClob("", null as NClob?) }
        assertNotSupported { rs.updateSQLXML(0, null) }
        assertNotSupported { rs.updateSQLXML("", null) }
        assertNotSupported { rs.updateNCharacterStream(0, null, 0) }
        assertNotSupported { rs.updateNCharacterStream("", null, 0) }
        assertNotSupported { rs.updateAsciiStream(0, null, 0L) }
        assertNotSupported { rs.updateBinaryStream(0, null, 0L) }
        assertNotSupported { rs.updateCharacterStream(0, null, 0L) }
        assertNotSupported { rs.updateAsciiStream("", null, 0L) }
        assertNotSupported { rs.updateBinaryStream("", null, 0L) }
        assertNotSupported { rs.updateCharacterStream("", null, 0L) }
        assertNotSupported { rs.updateBlob(0, null, 0) }
        assertNotSupported { rs.updateBlob("", null, 0) }
        assertNotSupported { rs.updateClob(0, null, 0) }
        assertNotSupported { rs.updateClob("", null, 0) }
        assertNotSupported { rs.updateNClob(0, null, 0) }
        assertNotSupported { rs.updateNClob("", null, 0) }
        assertNotSupported { rs.updateNCharacterStream(0, null) }
        assertNotSupported { rs.updateNCharacterStream("", null) }
        assertNotSupported { rs.updateAsciiStream(0, null) }
        assertNotSupported { rs.updateBinaryStream(0, null) }
        assertNotSupported { rs.updateCharacterStream(0, null) }
        assertNotSupported { rs.updateAsciiStream("", null) }
        assertNotSupported { rs.updateBinaryStream("", null) }
        assertNotSupported { rs.updateCharacterStream("", null) }
        assertNotSupported { rs.updateBlob(0, null as InputStream?) }
        assertNotSupported { rs.updateBlob("", null as InputStream?) }
        assertNotSupported { rs.updateClob(0, null as Reader?) }
        assertNotSupported { rs.updateClob("", null as Reader?) }
        assertNotSupported { rs.updateNClob(0, null as Reader?) }
        assertNotSupported { rs.updateNClob("", null as Reader?) }
        assertNotSupported { rs.updateObject(0, null, null, 0) }
        assertNotSupported { rs.updateObject("", null, null, 0) }
        assertNotSupported { rs.updateObject(0, null, null) }
        assertNotSupported { rs.updateObject("", null, null) }

        rs.close()
    }
}
