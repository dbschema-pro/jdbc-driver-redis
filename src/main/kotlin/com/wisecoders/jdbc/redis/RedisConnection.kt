package com.wisecoders.jdbc.redis

import java.io.IOException
import java.sql.Blob
import java.sql.CallableStatement
import java.sql.Clob
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.NClob
import java.sql.PreparedStatement
import java.sql.SQLClientInfoException
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Savepoint
import java.sql.Statement
import java.sql.Struct
import java.util.Properties
import java.util.concurrent.Executor

class RedisConnection constructor(
    io: RedisIO,
    db: Int,
    info: Properties
) : Connection {
    private var io: RedisIO? = null
    private var isClosed = true
    private var autoCommit = true
    private var db = 0
    private val maxRetries = 3
    private val maxTimeout = 3000

    init {
        this.io = io
        isClosed = false
        this.db = db

        // we got a connection, let's try to authenticate
        val passwd = info.getProperty(PROPERTY_PASSWORD)
        if ( passwd != null && passwd.isNotEmpty() ) {
            try {
                RedisCommandProcessor.runCommand(
                    this, (RedisCommand.AUTH.toString() + " " + passwd)
                )
            } catch (e: RedisParseException) {
                throw SQLException(e)
            } catch (e: RedisResultException) {
                throw SQLException("Could not authenticate with Redis.", e)
            }
        }


        if (db > 0) {
            try {
                RedisCommandProcessor.runCommand(this, RedisCommand.SELECT.toString() + " " + db)
            } catch (e: RedisParseException) {
                throw SQLException(e)
            } catch (e: RedisResultException) {
                throw SQLException("Could not SELECT database $db.", e)
            }
        }
    }

    @Throws(SQLException::class)
    override fun clearWarnings() {
        // no op.
    }

    /**
     * Issues a QUIT command to Redis server and closes any socket opened.
     * No operations should be done in the connection object after call
     * this method.
     */
    @Throws(SQLException::class)
    override fun close() {
        isClosed = true
        try {
            io!!.sendRaw(RedisCommand.QUIT.toString() + "\r\n")
            io!!.close()
        } catch (e: IOException) {
            throw SQLException(e)
        } catch (e: RedisResultException) {
            throw SQLException(e)
        }
    }

    /**
     * Send a SAVE command to Redis server.
     */
    @Throws(SQLException::class)
    override fun commit() {
        try {
            io!!.sendRaw(RedisCommand.SAVE.toString() + "\r\n")
        } catch (e: IOException) {
            throw SQLException(e)
        } catch (e: RedisResultException) {
            throw SQLException(e)
        }
    }

    @Throws(SQLException::class)
    override fun createArrayOf(
        typeName: String,
        elements: Array<Any>
    ): java.sql.Array {
        throw SQLFeatureNotSupportedException("createArrayOf")
    }

    @Throws(SQLException::class)
    override fun createBlob(): Blob {
        throw SQLFeatureNotSupportedException("createBlob")
    }

    @Throws(SQLException::class)
    override fun createClob(): Clob {
        throw SQLFeatureNotSupportedException("createClob")
    }

    @Throws(SQLException::class)
    override fun createNClob(): NClob {
        throw SQLFeatureNotSupportedException("createNClob")
    }

    @Throws(SQLException::class)
    override fun createSQLXML(): SQLXML {
        throw SQLFeatureNotSupportedException("createSQLXML")
    }

    @Throws(SQLException::class)
    override fun createStatement(): Statement {
        return RedisStatement(this)
    }

    @Throws(SQLException::class)
    override fun createStatement(
        resultSetType: Int,
        resultSetConcurrency: Int
    ): Statement {
        return RedisStatement(this)
    }

    @Throws(SQLException::class)
    override fun createStatement(
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): Statement {
        return RedisStatement(this)
    }

    @Throws(SQLException::class)
    override fun createStruct(
        typeName: String,
        attributes: Array<Any>
    ): Struct {
        throw SQLFeatureNotSupportedException("createStruct")
    }

    @Throws(SQLException::class)
    override fun getAutoCommit(): Boolean {
        return autoCommit
    }

    @Throws(SQLException::class)
    override fun getCatalog(): String? {
        return null
    }

    @Throws(SQLException::class)
    override fun getClientInfo(): Properties {
        throw SQLFeatureNotSupportedException("getClientInfo")
    }

    @Throws(SQLException::class)
    override fun getClientInfo(name: String): String {
        throw SQLFeatureNotSupportedException("getClientInfo")
    }

    @Throws(SQLException::class)
    override fun getHoldability(): Int {
        throw SQLFeatureNotSupportedException("getHoldability")
    }

    @Throws(SQLException::class)
    override fun getMetaData(): DatabaseMetaData {
        return RedisDatabaseMetaData()
    }

    @Throws(SQLException::class)
    override fun getTransactionIsolation(): Int {
        return Connection.TRANSACTION_NONE
    }

    @Throws(SQLException::class)
    override fun getTypeMap(): Map<String, Class<*>> {
        throw SQLFeatureNotSupportedException("getTypeMap")
    }

    @Throws(SQLException::class)
    override fun getWarnings(): SQLWarning? {
        return null
    }

    @Throws(SQLException::class)
    override fun isClosed(): Boolean {
        return isClosed
    }

    @Throws(SQLException::class)
    override fun isReadOnly(): Boolean {
        return false
    }

    @Throws(SQLException::class)
    override fun isValid(timeout: Int): Boolean {
        return isClosed
    }

    @Throws(SQLException::class)
    override fun nativeSQL(sql: String): String {
        throw SQLFeatureNotSupportedException("nativeSQL")
    }

    @Throws(SQLException::class)
    override fun prepareCall(sql: String): CallableStatement {
        return prepareCall(sql, 0, 0, 0)
    }

    @Throws(SQLException::class)
    override fun prepareCall(
        sql: String,
        resultSetType: Int,
        resultSetConcurrency: Int
    ): CallableStatement {
        return prepareCall(sql, 0, 0, 0)
    }

    @Throws(SQLException::class)
    override fun prepareCall(
        sql: String,
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): CallableStatement {
        throw SQLFeatureNotSupportedException("prepareCall")
    }

    @Throws(SQLException::class)
    override fun prepareStatement(sql: String): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun prepareStatement(
        sql: String,
        autoGeneratedKeys: Int
    ): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun prepareStatement(
        sql: String,
        columnIndexes: IntArray
    ): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun prepareStatement(
        sql: String,
        columnNames: Array<String>
    ): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun prepareStatement(
        sql: String,
        resultSetType: Int,
        resultSetConcurrency: Int
    ): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun prepareStatement(
        sql: String,
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): PreparedStatement {
        return RedisPreparedStatement(sql, this)
    }

    @Throws(SQLException::class)
    override fun releaseSavepoint(savepoint: Savepoint) {
        throw SQLFeatureNotSupportedException("releaseSavepoin")
    }

    @Throws(SQLException::class)
    override fun rollback() {
        rollback(null)
    }

    @Throws(SQLException::class)
    override fun rollback(savepoint: Savepoint?) {
        rollback(null)
    }

    @Throws(SQLException::class)
    override fun setAutoCommit(autoCommit: Boolean) {
        this.autoCommit = autoCommit
    }

    @Throws(SQLException::class)
    override fun setCatalog(catalog: String) {
        throw SQLFeatureNotSupportedException("setCatalog")
    }

    @Throws(SQLClientInfoException::class)
    override fun setClientInfo(properties: Properties) {
    }

    @Throws(SQLClientInfoException::class)
    override fun setClientInfo(
        name: String,
        value: String
    ) {
    }

    @Throws(SQLException::class)
    override fun setHoldability(holdability: Int) {
        throw SQLFeatureNotSupportedException("setHoldability")
    }

    @Throws(SQLException::class)
    override fun setReadOnly(readOnly: Boolean) {
        throw SQLFeatureNotSupportedException("setReadOnly")
    }

    @Throws(SQLException::class)
    override fun setSavepoint(): Savepoint {
        throw SQLFeatureNotSupportedException("setSavepoint")
    }

    @Throws(SQLException::class)
    override fun setSavepoint(name: String): Savepoint {
        throw SQLFeatureNotSupportedException("setSavepoint")
    }

    @Throws(SQLException::class)
    override fun setTransactionIsolation(level: Int) {
        throw SQLFeatureNotSupportedException("setSavepoint")
    }

    @Throws(SQLException::class)
    override fun setTypeMap(map: Map<String?, Class<*>?>?) {
    }

    @Throws(SQLException::class)
    override fun setSchema(schema: String) {
        throw SQLFeatureNotSupportedException("setSchema")
    }

    @Throws(SQLException::class)
    override fun getSchema(): String {
        throw SQLFeatureNotSupportedException("getSchema")
    }

    @Throws(SQLException::class)
    override fun abort(executor: Executor) {
        throw SQLFeatureNotSupportedException("abort")
    }

    @Throws(SQLException::class)
    override fun setNetworkTimeout(
        executor: Executor,
        milliseconds: Int
    ) {
        throw SQLFeatureNotSupportedException("setNetworkTimeout")
    }

    @Throws(SQLException::class)
    override fun getNetworkTimeout(): Int {
        throw SQLFeatureNotSupportedException("getNetworkTimeout")
    }

    @Throws(SQLException::class)
    override fun isWrapperFor(arg0: Class<*>?): Boolean {
        throw SQLFeatureNotSupportedException("isWrapperFor")
    }

    @Throws(SQLException::class)
    override fun <T> unwrap(arg0: Class<T>): T {
        throw SQLFeatureNotSupportedException("unwrap")
    }

    @Throws(SQLException::class)
    fun msgToServer(redisMsg: String): Any {
        var iCounter = 0

        val _io = io ?: throw SQLException("No connection to Redis")

        while (iCounter < maxRetries) {
            try {
                return _io.sendRaw(redisMsg) ?:""
            } catch (e: Exception) {
                println("Connection to redis is closed: " + e.message)
                try {
                    _io.reconnect()
                } catch (io: Exception) {
                    println("Problem connecting to redis: " + io.message)
                }
                try {
                    Thread.sleep(maxTimeout.toLong())
                } catch (ie: InterruptedException) {
                    println("Could not interrupt thread: " + ie.message)
                }
            }
            iCounter++
        }

        throw SQLException("Could not connect to redis")
    }

    companion object {
        private const val PROPERTY_PASSWORD = "password"
    }
}
