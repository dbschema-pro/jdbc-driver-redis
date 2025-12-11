package com.wisecoders.jdbc.redis

import java.io.IOException
import java.net.UnknownHostException

interface RedisIO {
    @Throws(IOException::class, RedisResultException::class)
    fun sendRaw(command: String?): Any?

    @Throws(IOException::class)
    fun close()

    @Throws(UnknownHostException::class, IOException::class)
    fun reconnect()
}
