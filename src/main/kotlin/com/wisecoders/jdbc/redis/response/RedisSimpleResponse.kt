package com.wisecoders.jdbc.redis.response

import com.wisecoders.jdbc.redis.RedisConnection
import com.wisecoders.jdbc.redis.RedisResultSet
import java.sql.ResultSet

class RedisSimpleResponse private constructor() : RedisResponse {
    override fun processResponse(
        connection: RedisConnection,
        command: String,
        response: Any
    ): ResultSet {
        return RedisResultSet(arrayOf(response as String))
    }

    companion object {
        @JvmField
        val INSTANCE: RedisResponse = RedisSimpleResponse()
    }
}
