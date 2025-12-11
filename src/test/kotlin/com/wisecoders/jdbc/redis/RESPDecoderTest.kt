package com.wisecoders.jdbc.redis

import com.wisecoders.jdbc.redis.RESPDecoder.decode
import com.wisecoders.jdbc.redis.response.RedisInputStream
import java.io.ByteArrayInputStream
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class RESPDecoderTest : WithAssertions {
    @Test
    @Throws(Exception::class)
    fun simpleString() {
        assertThat<Any?>(decode(inputStreamOf("+OK\r\n"))).isEqualTo("OK")
    }

    @Test
    @Throws(Exception::class)
    fun error() {
        assertThatThrownBy {
            decode(inputStreamOf("-WRONGTYPE Operation against a key holding the wrong kind of value\r\n"))
        }.isInstanceOf(RedisResultException::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun bulkString() {
        assertThat<Any?>(decode(inputStreamOf("$6\r\nfoobar\r\n"))).isEqualTo("foobar")
    }

    @Test
    @Throws(Exception::class)
    fun emptyString() {
        assertThat<Any?>(decode(inputStreamOf("$0\r\n\r\n"))).isEqualTo("")
    }

    @Test
    @Throws(Exception::class)
    fun nullString() {
        assertThat<Any?>(decode(inputStreamOf("$-1\r\n"))).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun integer() {
        assertThat<Any?>(decode(inputStreamOf(":1000\r\n"))).isEqualTo("1000")
    }

    @Test
    @Throws(Exception::class)
    fun emptyArray() {
        @Suppress("UNCHECKED_CAST")
        assertThat<Any?>(decode(inputStreamOf("*0\r\n")) as Array<Any?>?).isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun simpleArray() {
        @Suppress("UNCHECKED_CAST")
        assertThat<Any?>(decode(inputStreamOf("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n")) as Array<Any?>?)
            .containsExactly("foo", "bar")
    }

    @Test
    @Throws(Exception::class)
    fun mixedArray() {
        @Suppress("UNCHECKED_CAST")
        assertThat<Any?>(decode(inputStreamOf("*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n")) as Array<Any?>?)
            .containsExactly("1", "2", "3", "4", "foobar")
    }

    @Test
    @Throws(Exception::class)
    fun nullArray() {
        assertThat<Any?>(decode(inputStreamOf("*-1\r\n"))).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun nullInArray() {
        @Suppress("UNCHECKED_CAST")
        assertThat<Any?>(decode(inputStreamOf("*3\r\n$3\r\nfoo\r\n$-1\r\n$3\r\nbar\r\n")) as Array<Any?>?)
            .containsExactly("foo", null, "bar")
    }

    @Test
    @Throws(Exception::class)
    fun arrayOfArray() {
        @Suppress("UNCHECKED_CAST")
        assertThat<Any?>(decode(inputStreamOf("*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n+Bar\r\n")) as Array<Any?>?)
            .containsExactly(arrayOf<Any>("1", "2", "3"), arrayOf<Any>("Foo", "Bar"))
    }

    companion object {
        private fun inputStreamOf(message: String): RedisInputStream {
            return RedisInputStream(ByteArrayInputStream(message.toByteArray()))
        }
    }
}
