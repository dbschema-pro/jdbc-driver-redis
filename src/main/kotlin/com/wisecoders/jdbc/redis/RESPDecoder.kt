package com.wisecoders.jdbc.redis

import com.wisecoders.jdbc.redis.response.RedisInputStream
import java.io.IOException

object RESPDecoder {
    private const val RESP_SIMPLE_STRING = '+'.code.toByte()
    private const val RESP_ERROR = '-'.code.toByte()
    private const val RESP_INTEGER = ':'.code.toByte()
    private const val RESP_BULK_STRING = '$'.code.toByte()
    private const val RESP_ARRAY = '*'.code.toByte()

    @JvmStatic
    @Throws(RedisResultException::class, IOException::class)
    fun decode(inputStream: RedisInputStream): Any? {
        val type = inputStream.readByte()
        return when (type) {
            RESP_SIMPLE_STRING, RESP_INTEGER -> parseSimpleString(inputStream)
            RESP_BULK_STRING -> parseBulkString(inputStream)
            RESP_ERROR -> parseError(inputStream)
            RESP_ARRAY -> parseArray(inputStream)
            else -> null
        }
    }

    @Throws(IOException::class)
    private fun parseSimpleString(inputStream: RedisInputStream): String {
        return inputStream.readLine()
    }

    @Throws(IOException::class)
    private fun parseBulkString(inputStream: RedisInputStream): String? {
        val length = inputStream.readIntCrLf()
        if (length == -1) {
            return null
        }

        val read = ByteArray(length)
        var offset = 0
        while (offset < length) {
            val size = inputStream.read(read, offset, (length - offset))
            if (size == -1) throw IOException("It seems like server has closed the connection.")
            offset += size
        }

        // read 2 more bytes for the command delimiter
        inputStream.readByte()
        inputStream.readByte()

        return String(read)
    }

    @Throws(RedisResultException::class, IOException::class)
    private fun parseArray(inputStream: RedisInputStream): Any? {
        val num = inputStream.readIntCrLf()
        if (num == -1) {
            return null
        }
        val result: MutableList<Any?> = ArrayList(num)
        for (i in 0..<num) {
            result.add(decode(inputStream))
        }
        return result.toTypedArray()
    }


    @Throws(RedisResultException::class, IOException::class)
    private fun parseError(inputStream: RedisInputStream): String {
        val message = inputStream.readLine()
        throw RedisResultException(message)
    }
}
