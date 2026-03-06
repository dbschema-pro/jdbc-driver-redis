package com.wisecoders.jdbc.redis

import com.wisecoders.jdbc.redis.RESPDecoder.decode
import com.wisecoders.jdbc.redis.response.RedisInputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.Socket
import java.net.UnknownHostException

class RedisSocketIO(
    val host: String,
    val port: Int
) : RedisIO {
    private var socket: Socket? = null
    private val outputStreamWriter: OutputStreamWriter
    private var inputStream: RedisInputStream

    init {

        println("Connecting to socket $host:$port")
        this.socket = Socket(host, port)
        this.outputStreamWriter = OutputStreamWriter(socket!!.getOutputStream())
        this.inputStream = RedisInputStream(socket!!.getInputStream())
    }

    @Throws(IOException::class, RedisResultException::class)
    override fun sendRaw(command: String?): Any? {
        var decode: Any? = null

        if ( command != null ) {
            outputStreamWriter.write(command.toCharArray())
        }
        outputStreamWriter.flush()
        decode = decode(inputStream)

        return decode
    }

    @Throws(UnknownHostException::class, IOException::class)
    override fun reconnect() {
        try {
            println("Closing buffers and redis connection")
            close()
        } catch (e: IOException) {
            println("Could not close RedisSocketIO")
        }
    }

    @Throws(IOException::class)
    override fun close() {
        if (socket != null) {
            socket!!.close()
            this.socket = null
        }
    }
}
