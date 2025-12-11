/*
 * Copyright 2009-2010 MBTE Sweden AB. Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 */
/*
 * Copyright (c) 2010 Jonathan Leibiusky

 Permission is hereby granted, free of charge, to any person
 obtaining a copy of this software and associated documentation
 files (the "Software"), to deal in the Software without
 restriction, including without limitation the rights to use,
 copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the
 Software is furnished to do so, subject to the following
 conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 OTHER DEALINGS IN THE SOFTWARE.
 */
package com.wisecoders.jdbc.redis.response

import java.io.ByteArrayOutputStream
import java.io.FilterInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.math.min

/**
 * This class assumes (to some degree) that we are reading a RESP stream. As such it assumes certain
 * conventions regarding CRLF line termination. It also assumes that if the Protocol layer requires
 * a byte that if that byte is not there it is a stream error.
 */
class RedisInputStream @JvmOverloads constructor(
    `in`: InputStream?,
    size: Int = 8192
) :
    FilterInputStream(`in`) {
    protected val buf: ByteArray

    protected var count: Int = 0
    protected var limit: Int = 0

    init {
        require(size > 0) { "Buffer size <= 0" }
        buf = ByteArray(size)
    }

    @Throws(IOException::class)
    fun readByte(): Byte {
        ensureFill()
        return buf[count++]
    }

    @Throws(IOException::class)
    fun readLine(): String {
        val sb = StringBuilder()
        while (true) {
            ensureFill()

            val b = buf[count++]
            if (b == '\r'.code.toByte()) {
                ensureFill() // Must be one more byte

                val c = buf[count++]
                if (c == '\n'.code.toByte()) {
                    break
                }
                sb.append(Char(b.toUShort()))
                sb.append(Char(c.toUShort()))
            } else {
                sb.append(Char(b.toUShort()))
            }
        }

        val reply = sb.toString()
        if (reply.length == 0) {
            throw IOException("It seems like server has closed the connection.")
        }

        return reply
    }

    @Throws(IOException::class)
    fun readLineBytes(): ByteArray {
        /*
        * This operation should only require one fill. In that typical case we optimize allocation and
        * copy of the byte array. In the edge case where more than one fill is required then we take a
        * slower path and expand a byte array output stream as is necessary.
        */

        ensureFill()

        var pos = count
        val buf = this.buf
        while (true) {
            if (pos == limit) {
                return readLineBytesSlowly()
            }

            if (buf[pos++] == '\r'.code.toByte()) {
                if (pos == limit) {
                    return readLineBytesSlowly()
                }

                if (buf[pos++] == '\n'.code.toByte()) {
                    break
                }
            }
        }

        val N = (pos - count) - 2
        val line = ByteArray(N)
        System.arraycopy(buf, count, line, 0, N)
        count = pos
        return line
    }

    /**
     * Slow path in case a line of bytes cannot be read in one #fill() operation. This is still faster
     * than creating the StrinbBuilder, String, then encoding as byte[] in Protocol, then decoding
     * back into a String.
     */
    @Throws(IOException::class)
    private fun readLineBytesSlowly(): ByteArray {
        var bout: ByteArrayOutputStream? = null
        while (true) {
            ensureFill()

            val b = buf[count++]
            if (b == '\r'.code.toByte()) {
                ensureFill() // Must be one more byte

                val c = buf[count++]
                if (c == '\n'.code.toByte()) {
                    break
                }

                if (bout == null) {
                    bout = ByteArrayOutputStream(16)
                }

                bout.write(b.toInt())
                bout.write(c.toInt())
            } else {
                if (bout == null) {
                    bout = ByteArrayOutputStream(16)
                }

                bout.write(b.toInt())
            }
        }

        return if (bout == null) ByteArray(0) else bout.toByteArray()
    }

    @Throws(IOException::class)
    fun readIntCrLf(): Int {
        return readLongCrLf().toInt()
    }

    @Throws(IOException::class)
    fun readLongCrLf(): Long {
        val buf = this.buf

        ensureFill()

        val isNeg = buf[count] == '-'.code.toByte()
        if (isNeg) {
            ++count
        }

        var value: Long = 0
        while (true) {
            ensureFill()

            val b = buf[count++].toInt()
            if (b == '\r'.code) {
                ensureFill()

                if (buf[count++] != '\n'.code.toByte()) {
                    throw RuntimeException("Unexpected character!")
                }

                break
            } else {
                value = value * 10 + b - '0'.code.toLong()
            }
        }

        return (if (isNeg) -value else value)
    }

    @Throws(IOException::class)
    override fun read(
        b: ByteArray,
        off: Int,
        len: Int
    ): Int {
        ensureFill()

        val length = min((limit - count).toDouble(), len.toDouble()).toInt()
        System.arraycopy(buf, count, b, off, length)
        count += length
        return length
    }

    /**
     * These methods assume there are required bytes to be read. If we cannot read anymore bytes an
     * exception is thrown to quickly ascertain that the stream was smaller than expected.
     */
    @Throws(IOException::class)
    private fun ensureFill() {
        if (count >= limit) {
            limit = `in`.read(buf)
            count = 0
            if (limit == -1) {
                throw IOException("Unexpected end of stream.")
            }
        }
    }
}
