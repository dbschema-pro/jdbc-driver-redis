package com.wisecoders.jdbc.redis.response

import com.wisecoders.jdbc.redis.RedisConnection
import java.sql.ResultSet

interface RedisResponse {
    fun processResponse(
        connection: RedisConnection,
        command: String,
        response: Any
    ): ResultSet
}
