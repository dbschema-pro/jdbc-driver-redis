package com.wisecoders.jdbc.redis

import com.wisecoders.jdbc.redis.RedisCommandProcessor.runCommand
import java.sql.ResultSet
import java.sql.SQLException

abstract class RedisAbstractStatement {
    var _isClosed: Boolean = false
    var _connection: RedisConnection
    var _resultSet: ResultSet = RedisResultSet()

    var _sql: String? = null

    protected constructor(
        sql: String?,
        connection: RedisConnection
    ) {
        this._sql = sql
        this._connection = connection
    }

    protected constructor(connection: RedisConnection) {
        this._connection = connection
    }

    @Throws(SQLException::class)
    open fun execute(sql: String): Boolean {
        val _connection = _connection
        if (_isClosed ) throw SQLException("This statement is closed.")

        try {
            _resultSet = runCommand(_connection, sql)

            return true
        } catch (e: RedisParseException) {
            throw SQLException(e)
        } catch (e: RedisResultException) {
            throw SQLException(e)
        }
    }
}
