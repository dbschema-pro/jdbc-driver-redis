package com.wisecoders.jdbc.redis

import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.URL
import java.sql.Blob
import java.sql.Clob
import java.sql.Connection
import java.sql.Date
import java.sql.NClob
import java.sql.ParameterMetaData
import java.sql.PreparedStatement
import java.sql.Ref
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.RowId
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Time
import java.sql.Timestamp
import java.util.Calendar
import java.util.regex.Matcher

class RedisPreparedStatement(
    sql: String,
    conn: RedisConnection
) : RedisAbstractStatement(sql, conn), PreparedStatement {
    private val parameters: MutableMap<Int, String?> = HashMap()

    @Throws(SQLException::class)
    override fun addBatch() {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun clearParameters() {
        parameters.clear()
    }

    @Throws(SQLException::class)
    override fun execute(): Boolean {
        var _sql = _sql ?: throw SQLException("The statement SQL is empty.")
        if (this.isClosed) throw SQLException("This statement is closed.")

        var idx = 1
        while (_sql.indexOf("?") > 1) {
            try {
                val parameter = parameters[idx]
                _sql = _sql.replaceFirst(
                    "\\Q\u003F\\E".toRegex(),
                    if (parameter == null) "null" else Matcher.quoteReplacement(parameter)
                )
            } catch (e: IndexOutOfBoundsException) {
                throw SQLException("Can't find defined parameter for position: $idx")
            }
            idx++
        }

        return super.execute(_sql)
    }

    @Throws(SQLException::class)
    override fun executeQuery(): ResultSet? {
        this.execute()
        return this.resultSet
    }

    @Throws(SQLException::class)
    override fun executeUpdate(): Int {
        this.execute()
        return 0
    }

    @Throws(SQLException::class)
    override fun getMetaData(): ResultSetMetaData {
        return RedisResultSetMetaData()
    }

    @Throws(SQLException::class)
    override fun getParameterMetaData(): ParameterMetaData? {
        return null
    }

    @Throws(SQLException::class)
    override fun setArray(
        i: Int,
        x: java.sql.Array
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setAsciiStream(
        arg0: Int,
        arg1: InputStream
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setAsciiStream(
        parameterIndex: Int,
        x: InputStream,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setAsciiStream(
        arg0: Int,
        arg1: InputStream,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBigDecimal(
        parameterIndex: Int,
        x: BigDecimal
    ) {
        this.pushIntoParameters(parameterIndex, x.toPlainString())
    }

    @Throws(SQLException::class)
    override fun setBinaryStream(
        arg0: Int,
        arg1: InputStream
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBinaryStream(
        parameterIndex: Int,
        x: InputStream,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBinaryStream(
        arg0: Int,
        arg1: InputStream,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBlob(
        i: Int,
        x: Blob
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBlob(
        arg0: Int,
        arg1: InputStream
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBlob(
        arg0: Int,
        arg1: InputStream,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBoolean(
        parameterIndex: Int,
        x: Boolean
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Throws(SQLException::class)
    override fun setByte(
        parameterIndex: Int,
        x: Byte
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setBytes(
        parameterIndex: Int,
        x: ByteArray
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setCharacterStream(
        arg0: Int,
        arg1: Reader
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setCharacterStream(
        parameterIndex: Int,
        reader: Reader,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setCharacterStream(
        arg0: Int,
        arg1: Reader,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setClob(
        i: Int,
        x: Clob
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setClob(
        arg0: Int,
        arg1: Reader
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setClob(
        arg0: Int,
        arg1: Reader,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setDate(
        parameterIndex: Int,
        x: Date
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setDate(
        parameterIndex: Int,
        x: Date,
        cal: Calendar
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setDouble(
        parameterIndex: Int,
        x: Double
    ) {
    }

    @Throws(SQLException::class)
    override fun setFloat(
        parameterIndex: Int,
        x: Float
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Throws(SQLException::class)
    override fun setInt(
        parameterIndex: Int,
        x: Int
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Throws(SQLException::class)
    override fun setLong(
        parameterIndex: Int,
        x: Long
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Throws(SQLException::class)
    override fun setNCharacterStream(
        arg0: Int,
        arg1: Reader
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setNCharacterStream(
        arg0: Int,
        arg1: Reader,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setNClob(
        arg0: Int,
        arg1: NClob
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setNClob(
        arg0: Int,
        arg1: Reader
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setNClob(
        arg0: Int,
        arg1: Reader,
        arg2: Long
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setNString(
        arg0: Int,
        arg1: String
    ) {
        this.pushIntoParameters(arg0, arg1)
    }

    @Throws(SQLException::class)
    override fun setNull(
        parameterIndex: Int,
        sqlType: Int
    ) {
        this.pushIntoParameters(parameterIndex, "")
    }

    @Throws(SQLException::class)
    override fun setNull(
        parameterIndex: Int,
        sqlType: Int,
        typeName: String
    ) {
        this.pushIntoParameters(parameterIndex, "")
    }

    @Throws(SQLException::class)
    override fun setObject(
        parameterIndex: Int,
        x: Any?
    ) {
        this.pushIntoParameters(parameterIndex, x?.toString() ?: "")
    }

    @Throws(SQLException::class)
    override fun setObject(
        parameterIndex: Int,
        x: Any?,
        targetSqlType: Int
    ) {
        this.pushIntoParameters(parameterIndex, x?.toString() ?: "")
    }

    @Throws(SQLException::class)
    override fun setObject(
        parameterIndex: Int,
        x: Any,
        targetSqlType: Int,
        scale: Int
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setRef(
        i: Int,
        x: Ref
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setRowId(
        arg0: Int,
        arg1: RowId
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setSQLXML(
        arg0: Int,
        arg1: SQLXML
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setShort(
        parameterIndex: Int,
        x: Short
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Throws(SQLException::class)
    override fun setString(
        parameterIndex: Int,
        x: String?
    ) {
        this.pushIntoParameters(parameterIndex, x)
    }

    @Throws(SQLException::class)
    override fun setTime(
        parameterIndex: Int,
        x: Time
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setTime(
        parameterIndex: Int,
        x: Time,
        cal: Calendar
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setTimestamp(
        parameterIndex: Int,
        x: Timestamp
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setTimestamp(
        parameterIndex: Int,
        x: Timestamp,
        cal: Calendar
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setURL(
        parameterIndex: Int,
        x: URL
    ) {
        this.pushIntoParameters(parameterIndex, x.toString())
    }

    @Deprecated("Deprecated in Java")
    @Throws(SQLException::class)
    override fun setUnicodeStream(
        parameterIndex: Int,
        x: InputStream,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun addBatch(sql: String) {
    }

    @Throws(SQLException::class)
    override fun cancel() {
    }

    @Throws(SQLException::class)
    override fun clearBatch() {
    }

    @Throws(SQLException::class)
    override fun clearWarnings() {
    }

    @Throws(SQLException::class)
    override fun close() {
        _connection.close()
        _isClosed = true
    }

    @Throws(SQLException::class)
    override fun execute(sql: String): Boolean {
        this._sql = sql
        return this.execute()
    }

    @Throws(SQLException::class)
    override fun execute(
        sql: String,
        autoGeneratedKeys: Int
    ): Boolean {
        return execute(sql)
    }

    @Throws(SQLException::class)
    override fun execute(
        sql: String,
        columnIndexes: IntArray
    ): Boolean {
        return execute(sql)
    }

    @Throws(SQLException::class)
    override fun execute(
        sql: String,
        columnNames: Array<String>
    ): Boolean {
        return this.execute(sql)
    }

    @Throws(SQLException::class)
    override fun executeBatch(): IntArray? {
        return null
    }

    @Throws(SQLException::class)
    override fun executeQuery(sql: String): ResultSet? {
        this.execute(sql)
        return this.resultSet
    }

    @Throws(SQLException::class)
    override fun executeUpdate(sql: String): Int {
        this.execute(sql)
        return 0
    }

    @Throws(SQLException::class)
    override fun executeUpdate(
        sql: String,
        autoGeneratedKeys: Int
    ): Int {
        this.execute(sql)
        return 0
    }

    @Throws(SQLException::class)
    override fun executeUpdate(
        sql: String,
        columnIndexes: IntArray
    ): Int {
        this.execute(sql)
        return 0
    }

    @Throws(SQLException::class)
    override fun executeUpdate(
        sql: String,
        columnNames: Array<String>
    ): Int {
        this.execute(sql)
        return 0
    }

    @Throws(SQLException::class)
    override fun getConnection(): Connection {
        return _connection
    }

    @Throws(SQLException::class)
    override fun getFetchDirection(): Int {
        return ResultSet.FETCH_FORWARD
    }

    @Throws(SQLException::class)
    override fun getFetchSize(): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getGeneratedKeys(): ResultSet? {
        return null
    }

    @Throws(SQLException::class)
    override fun getMaxFieldSize(): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getMaxRows(): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getMoreResults(): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun getMoreResults(current: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun getQueryTimeout(): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getResultSet(): ResultSet {
        return this.resultSet
    }

    @Throws(SQLException::class)
    override fun getResultSetConcurrency(): Int {
        return ResultSet.CONCUR_READ_ONLY
    }

    @Throws(SQLException::class)
    override fun getResultSetHoldability(): Int {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT
    }

    @Throws(SQLException::class)
    override fun getResultSetType(): Int {
        return ResultSet.TYPE_FORWARD_ONLY
    }

    @Throws(SQLException::class)
    override fun getUpdateCount(): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getWarnings(): SQLWarning? {
        return null
    }

    @Throws(SQLException::class)
    override fun isClosed(): Boolean {
        return super._isClosed
    }

    @Throws(SQLException::class)
    override fun isPoolable(): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun setCursorName(name: String) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setEscapeProcessing(enable: Boolean) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setFetchDirection(direction: Int) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setFetchSize(rows: Int) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setMaxFieldSize(max: Int) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setMaxRows(max: Int) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setPoolable(arg0: Boolean) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun setQueryTimeout(seconds: Int) {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    override fun <T> unwrap(iface: Class<T>): T {
        throw SQLFeatureNotSupportedException()
    }

    @Throws(SQLException::class)
    private fun pushIntoParameters(
        index: Int,
        value: String?
    ) {
        if (index <= 0) throw SQLException("Invalid position for parameter ($index)")

        parameters[index] = value
    }

    @Throws(SQLException::class)
    override fun closeOnCompletion() {
    }

    @Throws(SQLException::class)
    override fun isCloseOnCompletion(): Boolean {
        return false
    }
}
