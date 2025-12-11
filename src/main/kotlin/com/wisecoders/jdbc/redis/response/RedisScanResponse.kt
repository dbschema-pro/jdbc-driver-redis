package com.wisecoders.jdbc.redis.response

import com.wisecoders.jdbc.redis.RedisConnection
import com.wisecoders.jdbc.redis.RedisCursorResultSet
import java.sql.ResultSet
import java.util.Arrays

class RedisScanResponse private constructor() : RedisResponse {
    override fun processResponse(
        connection: RedisConnection,
        command: String,
        response: Any
    ): ResultSet {
        val values = response as Array<Any>

        val cursor = (values[0] as String).toInt()
        val list = values[1] as Array<Any>

        return RedisCursorResultSet(
            connection, cursor, command,
            Arrays.copyOf(list, list.size, Array<String>::class.java)
        )
    }

    companion object {
        @JvmField
        val INSTANCE: RedisResponse = RedisScanResponse()
    }
}
