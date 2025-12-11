package com.wisecoders.jdbc.redis.response

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.PipedInputStream
import java.io.PipedOutputStream
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class RedisInputStreamTest : WithAssertions {
    @Test
    fun readByte() {
        val testData = "line1word1 line1word2\n line2word1\r\n"
        val ris = getRedisInputStream(testData)
        try {
            for (i in 0..<testData.length) {
                assertThat(ris.readByte()).isEqualTo(testData.toByteArray()[i])
            }
        } catch (e: IOException) {
            fail<Any?>("Unexpected IO Exception")
        }
    }

    @Test
    fun readLine() {
        val testData = "line1word1 line1word2\r\nline2word1\r\n"
        val ris = getRedisInputStream(testData)
        try {
            val line1 = ris.readLine()
            val line2 = ris.readLine()
            assertThat(line1).isEqualTo("line1word1 line1word2")
            assertThat(line2).isEqualTo("line2word1")
            assertThat(ris.available()).isZero()
        } catch (e: IOException) {
            fail<Any?>("Unexpected IO Exception")
        }
    }

    @Test
    fun readLineBytes() {
        val testData = "line1word1 line1word2\r\nline2word1\r\n"
        val ris = getRedisInputStream(testData)
        try {
            val lineBytes1 = ris.readLineBytes()
            val lineBytes2 = ris.readLineBytes()
            assertThat(String(lineBytes1)).isEqualTo("line1word1 line1word2")
            assertThat(String(lineBytes2)).isEqualTo("line2word1")
            assertThat(ris.available()).isZero()
        } catch (e: IOException) {
            fail<Any?>("Unexpected IO Exception")
        }
    }

    @Test
    fun readLineBytesWithSlowData() {
        try {
            val source = PipedOutputStream()
            val sink = PipedInputStream(source)
            val ris = RedisInputStream(sink)

            val sourceThread = Thread(object : Runnable {
                val INPUT: String = "line1word1 line1word2\r\nline2word1\r\n"
                val DELAY_MILLIS: Int = 50

                private var position = 0
                override fun run() {
                    try {
                        while (position < INPUT.length) {
                            Thread.sleep(DELAY_MILLIS.toLong())
                            source.write(INPUT.get(position).code)
                            position++
                        }
                    } catch (e: Exception) {
                        fail<Any?>("Unexpected exception while writing to piped input stream")
                    }
                }
            })
            sourceThread.start()

            val line1 = ris.readLineBytes()
            assertThat(String(line1)).isEqualTo("line1word1 line1word2")

            val line2 = ris.readLineBytes()
            assertThat(String(line2)).isEqualTo("line2word1")
        } catch (io: IOException) {
            fail<Any?>("Unexpected IO Exception")
        }
    }

    @Test
    fun readIntCRLF() {
        val testData = "12345\r\n-301\r\n1\r\n"
        val ris = getRedisInputStream(testData)
        try {
            assertThat(ris.readIntCrLf()).isEqualTo(12345)
            assertThat(ris.readIntCrLf()).isEqualTo(-301)
            assertThat(ris.readIntCrLf()).isEqualTo(1)
        } catch (e: IOException) {
            fail<Any?>("Unexpected IO Exception")
        }
    }

    private fun getRedisInputStream(data: String): RedisInputStream {
        val bais = ByteArrayInputStream(data.toByteArray())
        return RedisInputStream(bais)
    }
}
