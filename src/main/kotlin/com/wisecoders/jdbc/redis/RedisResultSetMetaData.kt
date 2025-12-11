package com.wisecoders.jdbc.redis

import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types

class RedisResultSetMetaData : ResultSetMetaData {
    @Throws(SQLException::class)
    override fun <T> unwrap(iface: Class<T>): T {
        throw SQLFeatureNotSupportedException("unwrap")
    }

    @Throws(SQLException::class)
    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw SQLFeatureNotSupportedException("isWrapperFor")
    }

    @Throws(SQLException::class)
    override fun getColumnCount(): Int {
        return 1
    }

    @Throws(SQLException::class)
    override fun isAutoIncrement(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun isCaseSensitive(column: Int): Boolean {
        return true
    }

    @Throws(SQLException::class)
    override fun isSearchable(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun isCurrency(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun isNullable(column: Int): Int {
        return ResultSetMetaData.columnNoNulls
    }

    @Throws(SQLException::class)
    override fun isSigned(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun getColumnDisplaySize(column: Int): Int {
        return MAX_SIZE
    }

    @Throws(SQLException::class)
    override fun getColumnLabel(column: Int): String {
        return ""
    }

    @Throws(SQLException::class)
    override fun getColumnName(column: Int): String {
        return ""
    }

    @Throws(SQLException::class)
    override fun getSchemaName(column: Int): String {
        return ""
    }

    @Throws(SQLException::class)
    override fun getPrecision(column: Int): Int {
        return MAX_SIZE
    }

    @Throws(SQLException::class)
    override fun getScale(column: Int): Int {
        return 0
    }

    @Throws(SQLException::class)
    override fun getTableName(column: Int): String {
        return ""
    }

    @Throws(SQLException::class)
    override fun getCatalogName(column: Int): String {
        return ""
    }

    @Throws(SQLException::class)
    override fun getColumnType(column: Int): Int {
        return Types.NVARCHAR
    }

    @Throws(SQLException::class)
    override fun getColumnTypeName(column: Int): String {
        return "String"
    }

    @Throws(SQLException::class)
    override fun isReadOnly(column: Int): Boolean {
        return true
    }

    @Throws(SQLException::class)
    override fun isWritable(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun isDefinitelyWritable(column: Int): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun getColumnClassName(column: Int): String {
        return "java.lang.String"
    }

    companion object {
        const val MAX_SIZE: Int = 1024
    }
}
