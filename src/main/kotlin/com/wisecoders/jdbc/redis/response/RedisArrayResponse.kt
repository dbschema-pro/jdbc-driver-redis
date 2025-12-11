package com.wisecoders.jdbc.redis.response

import com.wisecoders.jdbc.redis.RedisConnection
import com.wisecoders.jdbc.redis.RedisResultSet
import java.sql.ResultSet
import java.util.Arrays

class RedisArrayResponse private constructor() : RedisResponse {
    override fun processResponse(
        connection: RedisConnection,
        command: String,
        response: Any
    ): ResultSet {
        val list = response as Array<Any>
        return RedisResultSet(
            Arrays.copyOf(
                list, list.size,
                Array<String>::class.java
            )
        )
    }

    companion object {
        @JvmField
        val INSTANCE: RedisResponse = RedisArrayResponse()
    }
}
