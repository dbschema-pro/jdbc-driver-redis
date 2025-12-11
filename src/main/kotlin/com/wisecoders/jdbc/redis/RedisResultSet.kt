package com.wisecoders.jdbc.redis

import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.sql.Blob
import java.sql.Clob
import java.sql.Date
import java.sql.NClob
import java.sql.Ref
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.RowId
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Statement
import java.sql.Time
import java.sql.Timestamp
import java.sql.Types
import java.util.Calendar

open class RedisResultSet( protected var result: Array<String?> = emptyArray<String?>()) : ResultSet {
    private var isClosed = false
    protected var position: Int = -1

    @Throws(SQLException::class)
    override fun next(): Boolean {
        checkIfClosed()
        if (position < result.size - 1) {
            position++
            return true
        } else {
            return false
        }
    }

    @Throws(SQLException::class)
    override fun previous(): Boolean {
        throw SQLFeatureNotSupportedException("previous")
    }

    @Throws(SQLException::class)
    override fun isAfterLast(): Boolean {
        checkIfClosed()
        return position >= result.size
    }

    @Throws(SQLException::class)
    override fun isBeforeFirst(): Boolean {
        checkIfClosed()
        //TODO: valid for SCAN
        return position < 0
    }

    @Throws(SQLException::class)
    override fun isFirst(): Boolean {
        checkIfClosed()
        //TODO: valid for SCAN
        return position == 0
    }

    @Throws(SQLException::class)
    override fun first(): Boolean {
        checkIfClosed()
        position = 0
        return result.isNotEmpty()
    }

    @Throws(SQLException::class)
    override fun isLast(): Boolean {
        checkIfClosed()
        return position == result.size - 1
    }

    @Throws(SQLException::class)
    override fun last(): Boolean {
        position = result.size - 1
        return result.isNotEmpty()
    }

    @Throws(SQLException::class)
    override fun absolute(row: Int): Boolean {
        checkIfClosed()
        return false
    }

    @Throws(SQLException::class)
    override fun relative(rows: Int): Boolean {
        throw SQLFeatureNotSupportedException("relative")
    }

    @Throws(SQLException::class)
    override fun afterLast() {
        checkIfClosed()
        position = result.size
    }

    @Throws(SQLException::class)
    override fun beforeFirst() {
        checkIfClosed()
        position = -1
    }

    @Throws(SQLException::class)
    override fun cancelRowUpdates() {
        throw SQLFeatureNotSupportedException("cancelRowUpdates")
    }

    @Throws(SQLException::class)
    override fun clearWarnings() {
        checkIfClosed()
    }

    @Throws(SQLException::class)
    override fun close() {
        isClosed = true
    }

    @Throws(SQLException::class)
    override fun deleteRow() {
        throw SQLFeatureNotSupportedException("deleteRow")
    }

    @Throws(SQLException::class)
    override fun findColumn(columnName: String): Int {
        checkIfClosed()
        return 0
    }

    @Throws(SQLException::class)
    override fun getArray(columnIndex: Int): java.sql.Array {
        throw SQLFeatureNotSupportedException("getArray")
    }

    @Throws(SQLException::class)
    override fun getArray(colName: String): java.sql.Array {
        throw SQLFeatureNotSupportedException("getArray")
    }

    @Throws(SQLException::class)
    override fun getAsciiStream(columnIndex: Int): InputStream {
        throw SQLFeatureNotSupportedException("getAsciiStream")
    }

    @Throws(SQLException::class)
    override fun getAsciiStream(columnName: String): InputStream {
        throw SQLFeatureNotSupportedException("getAsciiStream")
    }

    @Throws(SQLException::class)
    override fun getBigDecimal(columnIndex: Int): BigDecimal {
        checkIfClosed()
        try {
            return BigDecimal(getString(0))
        } catch (_: NumberFormatException) {
            throw SQLException("Can't convert " + getString(0) + " to BigDecimal.")
        }
    }

    @Throws(SQLException::class)
    override fun getBigDecimal(columnName: String): BigDecimal {
        return getBigDecimal(0)
    }

    @Deprecated("Deprecated in Java")
    @Throws(SQLException::class)
    override fun getBigDecimal(
        columnIndex: Int,
        scale: Int
    ): BigDecimal {
        checkIfClosed()
        try {
            return BigDecimal(getString(0)).setScale(scale)
        } catch (_: NumberFormatException) {
            throw SQLException("Can't convert " + getString(0) + " to BigDecimal.")
        }
    }

    @Deprecated("Deprecated in Java")
    @Throws(SQLException::class)
    override fun getBigDecimal(
        columnName: String,
        scale: Int
    ): BigDecimal {
        return BigDecimal(0)
    }

    @Throws(SQLException::class)
    override fun getBinaryStream(columnIndex: Int): InputStream {
        throw SQLFeatureNotSupportedException("getBinaryStream")
    }

    @Throws(SQLException::class)
    override fun getBinaryStream(columnName: String): InputStream {
        throw SQLFeatureNotSupportedException("getBinaryStream")
    }

    @Throws(SQLException::class)
    override fun getBlob(i: Int): Blob {
        throw SQLFeatureNotSupportedException("getBlob")
    }

    @Throws(SQLException::class)
    override fun getBlob(colName: String): Blob {
        throw SQLFeatureNotSupportedException("getBlob")
    }

    @Throws(SQLException::class)
    override fun getBoolean(columnIndex: Int): Boolean {
        checkIfClosed()
        val r = getString(0)
        return if (r == "0" || r == "false") {
            false
        } else if (r == "1" || r == "true") {
            true
        } else {
            throw SQLException("Don't know how to convert $r into a boolean.")
        }
    }

    @Throws(SQLException::class)
    override fun getBoolean(columnName: String): Boolean {
        return getBoolean(0)
    }

    /**
     * Will return the first byte of current row value. If the result has more
     * bytes only the first will be returned.
     */
    @Throws(SQLException::class)
    override fun getByte(columnIndex: Int): Byte {
        checkIfClosed()
        val str = getString(0)
        return if (!str.isNullOrEmpty()) {
            str.toByteArray()[0]
        } else {
            0
        }
    }

    @Throws(SQLException::class)
    override fun getByte(columnName: String): Byte {
        return getByte(0)
    }

    @Throws(SQLException::class)
    override fun getBytes(columnIndex: Int): ByteArray? {
        checkIfClosed()
        val str = getString(0)
        return if (!str.isNullOrEmpty()) {
            str.toByteArray()
        } else {
            null
        }
    }

    @Throws(SQLException::class)
    override fun getBytes(columnName: String): ByteArray? {
        return getBytes(0)
    }

    @Throws(SQLException::class)
    override fun getCharacterStream(columnIndex: Int): Reader {
        throw SQLFeatureNotSupportedException("getCharacterStream")
    }

    @Throws(SQLException::class)
    override fun getCharacterStream(columnName: String): Reader {
        throw SQLFeatureNotSupportedException("getCharacterStream")
    }

    @Throws(SQLException::class)
    override fun getClob(i: Int): Clob {
        throw SQLFeatureNotSupportedException("getClob")
    }

    @Throws(SQLException::class)
    override fun getClob(colName: String): Clob {
        throw SQLFeatureNotSupportedException("getClob")
    }

    @Throws(SQLException::class)
    override fun getConcurrency(): Int {
        checkIfClosed()
        return ResultSet.CONCUR_READ_ONLY
    }

    @Throws(SQLException::class)
    override fun getCursorName(): String {
        throw SQLFeatureNotSupportedException("getCursorName")
    }

    @Throws(SQLException::class)
    override fun getDate(columnIndex: Int): Date {
        throw SQLFeatureNotSupportedException("getDate")
    }

    @Throws(SQLException::class)
    override fun getDate(columnName: String): Date {
        throw SQLFeatureNotSupportedException("getDate")
    }

    @Throws(SQLException::class)
    override fun getDate(
        columnIndex: Int,
        cal: Calendar
    ): Date {
        throw SQLFeatureNotSupportedException("getDate")
    }

    @Throws(SQLException::class)
    override fun getDate(
        columnName: String,
        cal: Calendar
    ): Date {
        throw SQLFeatureNotSupportedException("getDate")
    }

    @Throws(SQLException::class)
    override fun getDouble(columnIndex: Int): Double {
        val value = getString(0) ?: return 0.0

        try {
            return value.toDouble()
        } catch (_: NumberFormatException) {
            throw SQLException("Invalid value for getDouble() - $value")
        }
    }

    @Throws(SQLException::class)
    override fun getDouble(columnName: String): Double {
        return getDouble(0)
    }

    @Throws(SQLException::class)
    override fun getFetchDirection(): Int {
        checkIfClosed()
        return ResultSet.TYPE_SCROLL_INSENSITIVE
    }

    @Throws(SQLException::class)
    override fun getFetchSize(): Int {
        checkIfClosed()
        return result.size
    }

    @Throws(SQLException::class)
    override fun getFloat(columnIndex: Int): Float {
        val value = getString(0) ?: return 0f

        try {
            return value.toFloat()
        } catch (_: NumberFormatException) {
            throw SQLException("Invalid value for getFloat() - $value")
        }
    }

    @Throws(SQLException::class)
    override fun getFloat(columnName: String): Float {
        return getFloat(0)
    }

    @Throws(SQLException::class)
    override fun getHoldability(): Int {
        throw SQLFeatureNotSupportedException("getHoldability")
    }

    @Throws(SQLException::class)
    override fun getInt(columnIndex: Int): Int {
        val value = getString(0) ?: return 0

        try {
            return value.toInt()
        } catch (_: NumberFormatException) {
            throw SQLException("Invalid value for getInt() - $value")
        }
    }

    @Throws(SQLException::class)
    override fun getInt(columnName: String): Int {
        checkIfClosed()
        return getInt(0)
    }

    @Throws(SQLException::class)
    override fun getLong(columnIndex: Int): Long {
        val value = getString(0) ?: return 0

        try {
            return value.toLong()
        } catch (_: NumberFormatException) {
            throw SQLException("Invalid value for getLong() - $value")
        }
    }

    @Throws(SQLException::class)
    override fun getLong(columnName: String): Long {
        return getLong(0)
    }

    @Throws(SQLException::class)
    override fun getMetaData(): ResultSetMetaData {
        val result = this.result
        return object : ResultSetMetaData {
            @Throws(SQLException::class)
            override fun <T> unwrap(iface: Class<T>): T? {
                // TODO Auto-generated method stub
                return null
            }

            @Throws(SQLException::class)
            override fun isWrapperFor(iface: Class<*>?): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isWritable(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isSigned(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isSearchable(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isReadOnly(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isNullable(column: Int): Int {
                // TODO Auto-generated method stub
                return 0
            }

            @Throws(SQLException::class)
            override fun isDefinitelyWritable(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isCurrency(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isCaseSensitive(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun isAutoIncrement(column: Int): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            @Throws(SQLException::class)
            override fun getTableName(column: Int): String? {
                // TODO Auto-generated method stub
                return null
            }

            @Throws(SQLException::class)
            override fun getSchemaName(column: Int): String? {
                // TODO Auto-generated method stub
                return null
            }

            @Throws(SQLException::class)
            override fun getScale(column: Int): Int {
                // TODO Auto-generated method stub
                return 0
            }

            @Throws(SQLException::class)
            override fun getPrecision(column: Int): Int {
                // TODO Auto-generated method stub
                return 0
            }

            @Throws(SQLException::class)
            override fun getColumnTypeName(column: Int): String? {
                // TODO Auto-generated method stub
                return null
            }

            @Throws(SQLException::class)
            override fun getColumnType(column: Int): Int {
                // TODO Auto-generated method stub
                return Types.VARCHAR
            }

            @Throws(SQLException::class)
            override fun getColumnName(column: Int): String {
                return "Keys"
            }

            @Throws(SQLException::class)
            override fun getColumnLabel(column: Int): String {
                return "Keys"
            }

            @Throws(SQLException::class)
            override fun getColumnDisplaySize(column: Int): Int {
                // TODO Auto-generated method stub
                return 0
            }

            @Throws(SQLException::class)
            override fun getColumnCount(): Int {
                if (result.isEmpty()) return 0
                return 1
            }

            @Throws(SQLException::class)
            override fun getColumnClassName(column: Int): String? {
                // TODO Auto-generated method stub
                return null
            }

            @Throws(SQLException::class)
            override fun getCatalogName(column: Int): String? {
                // TODO Auto-generated method stub
                return null
            }
        }
    }

    @Throws(SQLException::class)
    override fun getNCharacterStream(columnIndex: Int): Reader {
        throw SQLFeatureNotSupportedException("getNCharacterStream")
    }

    @Throws(SQLException::class)
    override fun getNCharacterStream(columnName: String): Reader {
        return getNCharacterStream(0)
    }

    @Throws(SQLException::class)
    override fun getNClob(columnIndex: Int): NClob {
        throw SQLFeatureNotSupportedException("getNClob")
    }

    @Throws(SQLException::class)
    override fun getNClob(columnName: String): NClob {
        return getNClob(0)
    }

    @Throws(SQLException::class)
    override fun getNString(columnIndex: Int): String? {
        checkIfClosed()
        return result[position]
    }

    @Throws(SQLException::class)
    override fun getNString(columnName: String): String? {
        return getNString(0)
    }

    @Throws(SQLException::class)
    override fun getObject(columnIndex: Int): Any {
        return listOf(*this.result)
    }

    @Throws(SQLException::class)
    override fun getObject(columnName: String): Any {
        return getObject(0)
    }

    @Throws(SQLException::class)
    override fun getObject(
        columnIndex: Int,
        map: Map<String?, Class<*>?>?
    ): Any {
        throw SQLFeatureNotSupportedException("getObject")
    }

    @Throws(SQLException::class)
    override fun getObject(
        columnName: String,
        map: Map<String?, Class<*>?>?
    ): Any {
        return getObject(0, map)
    }

    @Throws(SQLException::class)
    override fun getRef(columnIndex: Int): Ref {
        throw SQLFeatureNotSupportedException("getRef")
    }

    @Throws(SQLException::class)
    override fun getRef(columnName: String): Ref {
        return getRef(0)
    }

    @Throws(SQLException::class)
    override fun getRow(): Int {
        checkIfClosed()
        return this.position + 1
    }

    @Throws(SQLException::class)
    override fun getRowId(columnIndex: Int): RowId {
        throw SQLFeatureNotSupportedException("getRowId")
    }

    @Throws(SQLException::class)
    override fun getRowId(columnName: String): RowId {
        return getRowId(0)
    }

    @Throws(SQLException::class)
    override fun getSQLXML(columnIndex: Int): SQLXML {
        throw SQLFeatureNotSupportedException("getSQLXML")
    }

    @Throws(SQLException::class)
    override fun getSQLXML(columnName: String): SQLXML {
        return getSQLXML(0)
    }

    @Throws(SQLException::class)
    override fun getShort(columnIndex: Int): Short {
        val value = getString(0) ?: return 0

        try {
            return value.toShort()
        } catch (_: NumberFormatException) {
            throw SQLException("Invalid value for getShort() - $value")
        }
    }

    @Throws(SQLException::class)
    override fun getShort(columnName: String): Short {
        return getShort(0)
    }

    @Throws(SQLException::class)
    override fun getStatement(): Statement? {
        checkIfClosed()
        //TODO: implement
        return null
    }

    @Throws(SQLException::class)
    override fun getString(columnIndex: Int): String? {
        checkIfClosed()

        if (isAfterLast || isBeforeFirst) {
            throw SQLException("columnIndex is not valid")
        }

        return result[position]
    }

    @Throws(SQLException::class)
    override fun getString(columnName: String): String? {
        return getString(0)
    }

    @Throws(SQLException::class)
    override fun getTime(columnIndex: Int): Time {
        throw SQLFeatureNotSupportedException("getTime")
    }

    @Throws(SQLException::class)
    override fun getTime(columnName: String): Time {
        throw SQLFeatureNotSupportedException("getTime")
    }

    @Throws(SQLException::class)
    override fun getTime(
        columnIndex: Int,
        cal: Calendar
    ): Time {
        throw SQLFeatureNotSupportedException("getTime")
    }

    @Throws(SQLException::class)
    override fun getTime(
        columnName: String,
        cal: Calendar
    ): Time {
        throw SQLFeatureNotSupportedException("getTime")
    }

    @Throws(SQLException::class)
    override fun getTimestamp(columnIndex: Int): Timestamp {
        throw SQLFeatureNotSupportedException("getTimestamp")
    }

    @Throws(SQLException::class)
    override fun getTimestamp(columnName: String): Timestamp {
        throw SQLFeatureNotSupportedException("getTimestamp")
    }

    @Throws(SQLException::class)
    override fun getTimestamp(
        columnIndex: Int,
        cal: Calendar
    ): Timestamp {
        throw SQLFeatureNotSupportedException("getTimestamp")
    }

    @Throws(SQLException::class)
    override fun getTimestamp(
        columnName: String,
        cal: Calendar
    ): Timestamp {
        throw SQLFeatureNotSupportedException("getTimestamp")
    }

    @Throws(SQLException::class)
    override fun getType(): Int {
        checkIfClosed()
        return ResultSet.TYPE_SCROLL_INSENSITIVE
    }

    @Throws(SQLException::class)
    override fun getURL(columnIndex: Int): URL {
        checkIfClosed()
        try {
            return URI(result[position]).toURL()
        } catch (e: MalformedURLException) {
            throw SQLException(e)
        }
    }

    @Throws(SQLException::class)
    override fun getURL(columnName: String): URL {
        return getURL(0)
    }

    @Deprecated("Deprecated in Java")
    @Throws(SQLException::class)
    override fun getUnicodeStream(columnIndex: Int): InputStream {
        throw SQLFeatureNotSupportedException("getUnicodeStream")
    }

    @Deprecated("Deprecated in Java")
    @Throws(SQLException::class)
    override fun getUnicodeStream(columnName: String): InputStream {
        throw SQLFeatureNotSupportedException("getUnicodeStream")
    }

    @Throws(SQLException::class)
    override fun getWarnings(): SQLWarning? {
        checkIfClosed()
        return null
    }

    @Throws(SQLException::class)
    override fun insertRow() {
        throw SQLFeatureNotSupportedException("insertRow")
    }

    @Throws(SQLException::class)
    override fun isClosed(): Boolean {
        return isClosed
    }

    @Throws(SQLException::class)
    override fun moveToCurrentRow() {
        throw SQLFeatureNotSupportedException("moveToCurrentRow")
    }

    @Throws(SQLException::class)
    override fun moveToInsertRow() {
        throw SQLFeatureNotSupportedException("moveToInsertRow")
    }

    @Throws(SQLException::class)
    override fun refreshRow() {
        throw SQLFeatureNotSupportedException("refreshRow")
    }

    @Throws(SQLException::class)
    override fun rowDeleted(): Boolean {
        throw SQLFeatureNotSupportedException("rowDeleted")
    }

    @Throws(SQLException::class)
    override fun rowInserted(): Boolean {
        throw SQLFeatureNotSupportedException("rowInserted")
    }

    @Throws(SQLException::class)
    override fun rowUpdated(): Boolean {
        throw SQLFeatureNotSupportedException("rowUpdated")
    }

    @Throws(SQLException::class)
    override fun setFetchDirection(direction: Int) {
        throw SQLFeatureNotSupportedException("setFetchDirection")
    }

    @Throws(SQLException::class)
    override fun setFetchSize(rows: Int) {
        throw SQLFeatureNotSupportedException("setFetchSize")
    }

    @Throws(SQLException::class)
    override fun updateArray(
        columnIndex: Int,
        x: java.sql.Array
    ) {
        throw SQLFeatureNotSupportedException("updateArray")
    }

    @Throws(SQLException::class)
    override fun updateArray(
        columnName: String,
        x: java.sql.Array
    ) {
        updateArray(0, x)
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnIndex: Int,
        x: InputStream
    ) {
        throw SQLFeatureNotSupportedException("updateAsciiStream")
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnIndex: String,
        x: InputStream
    ) {
        updateAsciiStream(0, x)
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnIndex: Int,
        x: InputStream,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException("updateAsciiStream")
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnName: String,
        x: InputStream,
        length: Int
    ) {
        updateAsciiStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnIndex: Int,
        x: InputStream,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateAsciiStream")
    }

    @Throws(SQLException::class)
    override fun updateAsciiStream(
        columnName: String,
        x: InputStream,
        length: Long
    ) {
        updateAsciiStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateBigDecimal(
        columnIndex: Int,
        x: BigDecimal
    ) {
        throw SQLFeatureNotSupportedException("updateBigDecimal")
    }

    @Throws(SQLException::class)
    override fun updateBigDecimal(
        columnName: String,
        x: BigDecimal
    ) {
        updateBigDecimal(0, x)
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnIndex: Int,
        x: InputStream
    ) {
        throw SQLFeatureNotSupportedException("updateBinaryStream")
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnName: String,
        x: InputStream
    ) {
        updateBinaryStream(0, x)
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnIndex: Int,
        x: InputStream,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException("updateBinaryStream")
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnName: String,
        x: InputStream,
        length: Int
    ) {
        updateBinaryStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnIndex: Int,
        x: InputStream,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateBinaryStream")
    }

    @Throws(SQLException::class)
    override fun updateBinaryStream(
        columnName: String,
        x: InputStream,
        length: Long
    ) {
        updateBinaryStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnIndex: Int,
        x: Blob
    ) {
        throw SQLFeatureNotSupportedException("updateBlob")
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnName: String,
        x: Blob
    ) {
        updateBlob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnIndex: Int,
        x: InputStream
    ) {
        throw SQLFeatureNotSupportedException("updateBlob")
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnName: String,
        x: InputStream
    ) {
        updateBlob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnIndex: Int,
        x: InputStream,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateBlob")
    }

    @Throws(SQLException::class)
    override fun updateBlob(
        columnName: String,
        x: InputStream,
        length: Long
    ) {
        updateBlob(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateBoolean(
        columnIndex: Int,
        x: Boolean
    ) {
        throw SQLFeatureNotSupportedException("updateBoolean")
    }

    @Throws(SQLException::class)
    override fun updateBoolean(
        columnName: String,
        x: Boolean
    ) {
        updateBoolean(0, x)
    }

    @Throws(SQLException::class)
    override fun updateByte(
        columnIndex: Int,
        x: Byte
    ) {
        throw SQLFeatureNotSupportedException("updateByte")
    }

    @Throws(SQLException::class)
    override fun updateByte(
        columnName: String,
        x: Byte
    ) {
        updateByte(0, x)
    }

    @Throws(SQLException::class)
    override fun updateBytes(
        columnIndex: Int,
        x: ByteArray
    ) {
        throw SQLFeatureNotSupportedException("updateBytes")
    }

    @Throws(SQLException::class)
    override fun updateBytes(
        columnName: String,
        x: ByteArray
    ) {
        updateBytes(0, x)
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnIndex: Int,
        x: Reader
    ) {
        throw SQLFeatureNotSupportedException("updateCharacterStream")
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnName: String,
        x: Reader
    ) {
        updateCharacterStream(0, x)
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnIndex: Int,
        x: Reader,
        length: Int
    ) {
        throw SQLFeatureNotSupportedException("updateCharacterStream")
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnName: String,
        reader: Reader,
        length: Int
    ) {
        updateCharacterStream(0, reader, length)
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnIndex: Int,
        x: Reader,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateCharacterStream")
    }

    @Throws(SQLException::class)
    override fun updateCharacterStream(
        columnName: String,
        x: Reader,
        length: Long
    ) {
        updateCharacterStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnIndex: Int,
        x: Clob
    ) {
        throw SQLFeatureNotSupportedException("updateClob")
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnName: String,
        x: Clob
    ) {
        updateClob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnIndex: Int,
        x: Reader
    ) {
        throw SQLFeatureNotSupportedException("updateClob")
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnName: String,
        x: Reader
    ) {
        updateClob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnIndex: Int,
        x: Reader,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateClob")
    }

    @Throws(SQLException::class)
    override fun updateClob(
        columnName: String,
        x: Reader,
        length: Long
    ) {
        updateClob(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateDate(
        columnIndex: Int,
        x: Date
    ) {
        throw SQLFeatureNotSupportedException("updateDate")
    }

    @Throws(SQLException::class)
    override fun updateDate(
        columnName: String,
        x: Date
    ) {
        updateDate(0, x)
    }

    @Throws(SQLException::class)
    override fun updateDouble(
        columnIndex: Int,
        x: Double
    ) {
        throw SQLFeatureNotSupportedException("updateDouble")
    }

    @Throws(SQLException::class)
    override fun updateDouble(
        columnName: String,
        x: Double
    ) {
        updateDouble(0, x)
    }

    @Throws(SQLException::class)
    override fun updateFloat(
        columnIndex: Int,
        x: Float
    ) {
        throw SQLFeatureNotSupportedException("updateFloat")
    }

    @Throws(SQLException::class)
    override fun updateFloat(
        columnName: String,
        x: Float
    ) {
        updateFloat(0, x)
    }

    @Throws(SQLException::class)
    override fun updateInt(
        columnIndex: Int,
        x: Int
    ) {
        throw SQLFeatureNotSupportedException("updateInt")
    }

    @Throws(SQLException::class)
    override fun updateInt(
        columnName: String,
        x: Int
    ) {
        updateInt(0, x)
    }

    @Throws(SQLException::class)
    override fun updateLong(
        columnIndex: Int,
        x: Long
    ) {
        throw SQLFeatureNotSupportedException("updateLong")
    }

    @Throws(SQLException::class)
    override fun updateLong(
        columnName: String,
        x: Long
    ) {
        updateLong(0, x)
    }

    @Throws(SQLException::class)
    override fun updateNCharacterStream(
        columnIndex: Int,
        x: Reader
    ) {
        throw SQLFeatureNotSupportedException("updateNCharacterStream")
    }

    @Throws(SQLException::class)
    override fun updateNCharacterStream(
        columnName: String,
        x: Reader
    ) {
        updateNCharacterStream(0, x)
    }

    @Throws(SQLException::class)
    override fun updateNCharacterStream(
        columnIndex: Int,
        x: Reader,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateNCharacterStream")
    }

    @Throws(SQLException::class)
    override fun updateNCharacterStream(
        columnName: String,
        x: Reader,
        length: Long
    ) {
        updateNCharacterStream(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnIndex: Int,
        x: NClob
    ) {
        throw SQLFeatureNotSupportedException("updateNClob")
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnName: String,
        x: NClob
    ) {
        updateNClob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnIndex: Int,
        x: Reader
    ) {
        throw SQLFeatureNotSupportedException("updateNClob")
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnName: String,
        x: Reader
    ) {
        updateNClob(0, x)
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnIndex: Int,
        x: Reader,
        length: Long
    ) {
        throw SQLFeatureNotSupportedException("updateNClob")
    }

    @Throws(SQLException::class)
    override fun updateNClob(
        columnName: String,
        x: Reader,
        length: Long
    ) {
        updateNClob(0, x, length)
    }

    @Throws(SQLException::class)
    override fun updateNString(
        columnIndex: Int,
        x: String
    ) {
        throw SQLFeatureNotSupportedException("updateNString")
    }

    @Throws(SQLException::class)
    override fun updateNString(
        columnName: String,
        x: String
    ) {
        updateNString(0, x)
    }

    @Throws(SQLException::class)
    override fun updateNull(columnIndex: Int) {
        throw SQLFeatureNotSupportedException("updateNull")
    }

    @Throws(SQLException::class)
    override fun updateNull(columnName: String) {
        updateNull(0)
    }

    @Throws(SQLException::class)
    override fun updateObject(
        columnIndex: Int,
        x: Any
    ) {
        throw SQLFeatureNotSupportedException("updateObject")
    }

    @Throws(SQLException::class)
    override fun updateObject(
        columnName: String,
        x: Any
    ) {
        updateObject(0, x)
    }

    @Throws(SQLException::class)
    override fun updateObject(
        columnIndex: Int,
        x: Any,
        scaleOrLength: Int
    ) {
        throw SQLFeatureNotSupportedException("updateObject")
    }

    @Throws(SQLException::class)
    override fun updateObject(
        columnName: String,
        x: Any,
        scaleOrLength: Int
    ) {
        updateObject(0, x, scaleOrLength)
    }

    @Throws(SQLException::class)
    override fun updateRef(
        columnIndex: Int,
        x: Ref
    ) {
        throw SQLFeatureNotSupportedException("updateRef")
    }

    @Throws(SQLException::class)
    override fun updateRef(
        columnName: String,
        x: Ref
    ) {
        updateRef(0, x)
    }

    @Throws(SQLException::class)
    override fun updateRow() {
        throw SQLFeatureNotSupportedException("updateRow")
    }

    @Throws(SQLException::class)
    override fun updateRowId(
        columnIndex: Int,
        x: RowId
    ) {
        throw SQLFeatureNotSupportedException("updateRowId")
    }

    @Throws(SQLException::class)
    override fun updateRowId(
        columnName: String,
        x: RowId
    ) {
        updateRowId(0, x)
    }

    @Throws(SQLException::class)
    override fun updateSQLXML(
        columnIndex: Int,
        x: SQLXML
    ) {
        throw SQLFeatureNotSupportedException("updateSQLXML")
    }

    @Throws(SQLException::class)
    override fun updateSQLXML(
        columnName: String,
        x: SQLXML
    ) {
        updateSQLXML(0, x)
    }

    @Throws(SQLException::class)
    override fun updateShort(
        columnIndex: Int,
        x: Short
    ) {
        throw SQLFeatureNotSupportedException("updateShort")
    }

    @Throws(SQLException::class)
    override fun updateShort(
        columnName: String,
        x: Short
    ) {
        updateShort(0, x)
    }

    @Throws(SQLException::class)
    override fun updateString(
        columnIndex: Int,
        x: String
    ) {
        throw SQLFeatureNotSupportedException("updateString")
    }

    @Throws(SQLException::class)
    override fun updateString(
        columnName: String,
        x: String
    ) {
        updateString(0, x)
    }

    @Throws(SQLException::class)
    override fun updateTime(
        columnIndex: Int,
        x: Time
    ) {
        throw SQLFeatureNotSupportedException("updateTime")
    }

    @Throws(SQLException::class)
    override fun updateTime(
        columnName: String,
        x: Time
    ) {
        updateTime(0, x)
    }

    @Throws(SQLException::class)
    override fun updateTimestamp(
        columnIndex: Int,
        x: Timestamp
    ) {
        throw SQLFeatureNotSupportedException("updateTimestamp")
    }

    @Throws(SQLException::class)
    override fun updateTimestamp(
        columnName: String,
        x: Timestamp
    ) {
        updateTimestamp(0, x)
    }

    @Throws(SQLException::class)
    override fun wasNull(): Boolean {
        checkIfClosed()
        return false
    }

    @Throws(SQLException::class)
    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw SQLFeatureNotSupportedException("isWrapperFor")
    }

    @Throws(SQLException::class)
    override fun <T> unwrap(iface: Class<T>): T {
        throw SQLFeatureNotSupportedException("unwrap")
    }

    @Throws(SQLException::class)
    override fun <T> getObject(
        columnIndex: Int,
        type: Class<T>
    ): T? {
        return null
    }

    @Throws(SQLException::class)
    override fun <T> getObject(
        columnLabel: String,
        type: Class<T>
    ): T? {
        return null
    }

    @Throws(SQLException::class)
    private fun checkIfClosed() {
        if (isClosed()) {
            throw SQLException()
        }
    }
}
