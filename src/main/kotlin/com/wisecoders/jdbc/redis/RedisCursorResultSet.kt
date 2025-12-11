package com.wisecoders.jdbc.redis

import java.sql.SQLException

class RedisCursorResultSet(
    private val connection: RedisConnection,
    var cursor: Int,
    private val command: String,
    results: Array<String?>
) :
    RedisResultSet(results) {
    @Throws(SQLException::class)
    override fun next(): Boolean {
        val next = super.next()

        if (!next && cursor != 0) {
            try {
                val newResultSet = RedisCommandProcessor.runCommand(
                    connection,
                    command.replaceFirst("\\d+".toRegex(), cursor.toString())
                ) as RedisCursorResultSet

                position = 0
                cursor = newResultSet.cursor
                result = newResultSet.result
                newResultSet.close()
                return true
            } catch (e: RedisParseException) {
                throw SQLException(e)
            } catch (e: RedisResultException) {
                throw SQLException(e)
            }
        }

        return next
    }

}
