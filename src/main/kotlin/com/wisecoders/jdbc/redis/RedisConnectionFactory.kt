package com.wisecoders.jdbc.redis

import java.io.IOException
import java.net.UnknownHostException
import java.sql.Connection
import java.sql.SQLException
import java.util.Properties

object RedisConnectionFactory {
    @Throws(SQLException::class)
    fun getConnection(
        host: String,
        port: Int,
        dbnb: Int,
        info: Properties
    ): Connection {
        //TODO: Add support for others RedisIOs

        val io: RedisIO
        try {
            io = RedisSocketIO(host, port)
        } catch (e: UnknownHostException) {
            throw SQLException("Can't find host: $host")
        } catch (e: IOException) {
            throw SQLException("Couldn't connect (" + e.message + ")")
        }

        val conn: Connection = RedisConnection(io, dbnb, info)
        return conn
    }
}
