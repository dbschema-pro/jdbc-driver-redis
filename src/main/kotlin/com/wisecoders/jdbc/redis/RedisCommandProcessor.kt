package com.wisecoders.jdbc.redis

import java.sql.ResultSet
import java.sql.SQLException

object RedisCommandProcessor {
    @JvmStatic
    @Throws(SQLException::class, RedisParseException::class, RedisResultException::class)
    fun runCommand(
        connection: RedisConnection,
        statement: String
    ): ResultSet {
        val command = extractCommand(statement)
        val response = connection.msgToServer(statement + "\r\n")
        return command.response.processResponse(connection, statement, response)
    }


    @Throws(RedisParseException::class)
    private fun extractCommand(statement: String): RedisCommand {
        val keywords = statement.trim().split(" ".toRegex(), limit = 2).toTypedArray()

        try {
            return enumValueOf<RedisCommand>(keywords[0].uppercase())
        } catch (e: IllegalArgumentException) {
            throw RedisParseException("Command not recognized.")
        }
    }
}
