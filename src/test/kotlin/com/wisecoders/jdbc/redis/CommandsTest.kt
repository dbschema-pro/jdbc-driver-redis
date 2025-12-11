package com.wisecoders.jdbc.redis

import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * These are simple tests to check if the commands are working
 * properly.
 * @author mavcunha
 */
@Disabled
class CommandsTest : WithAssertions {
    @Test
    @Throws(Exception::class)
    fun invalidCommand() {
        assertThatThrownBy(ThrowableAssert.ThrowingCallable { executeSingleStringResult("INVALID") })
            .isInstanceOf(SQLException::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun set() {
        val key: String = keyPrefix + "_SET"
        assertThat(executeSingleStringResult("SET $key value")).isEqualTo("OK")
        assertThat(retrieveValue(key)).isEqualTo("value")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun get() {
        val key: String = keyPrefix + "_GET"
        createValue(key, "value")
        assertThat(retrieveValue(key)).isEqualTo("value")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun mget() {
        val key: String = keyPrefix + "_MGET"
        createValue(key + 1, "value1")
        createValue(key + 2, "value2")

        val results = executeStringResults("MGET " + key + "1 " + key + "2")

        assertThat<String?>(results).hasSize(2)
        assertThat<String?>(results).contains("value1", "value2")

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun setnx() {
        val key: String = keyPrefix + "_SETNX_NON_EXISTENT_KEY"
        // let's store store a non-existent key, which should be true as return.
        assertThat(executeSingleBooleanResult("SETNX $key value")).isTrue()

        // now let's do it again, now SETNX should return false.
        assertThat(executeSingleBooleanResult("SETNX $key value")).isFalse()

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun incr() {
        val key: String = keyPrefix + "_INCR_TEST_KEY"
        assertThat(executeSingleIntegerResult("INCR $key")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("INCR $key")).isEqualTo(2)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun incrby() {
        val key: String = keyPrefix + "_INCRBY_TEST_KEY"
        assertThat(executeSingleIntegerResult("INCRBY $key 2")).isEqualTo(2)
        assertThat(executeSingleIntegerResult("INCRBY $key 10")).isEqualTo(12)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun decr() {
        val key: String = keyPrefix + "_DECR_TEST_KEY"
        assertThat(executeSingleIntegerResult("DECR $key")).isEqualTo(-1)
        assertThat(executeSingleIntegerResult("DECR $key")).isEqualTo(-2)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun decrby() {
        val key: String = keyPrefix + "_DECRBY_TEST_KEY"
        assertThat(executeSingleIntegerResult("DECRBY $key 1")).isEqualTo(-1)
        assertThat(executeSingleIntegerResult("DECRBY $key 10")).isEqualTo(-11)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun exists() {
        val key: String = keyPrefix + "_EXISTS_TEST_KEY"
        assertThat(executeSingleBooleanResult("EXISTS $key")).isFalse()
        createValue(key, "value")
        assertThat(executeSingleBooleanResult("EXISTS $key")).isTrue()
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun del() {
        val key: String = keyPrefix + "_DEL_TEST_KEY"

        // trying to del a non existent key should return false.
        assertThat(executeSingleBooleanResult("DEL $key")).isFalse()

        // del a key should return true...
        createValue(key, "value")
        assertThat(executeSingleBooleanResult("DEL $key")).isTrue()

        // the deleted key should be a null if we try to get it after the del.
        assertThat(retrieveValue(key)).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun type() {
        val stringKey: String = keyPrefix + "_TYPE_STRING_TEST_KEY"
        val listKey: String = keyPrefix + "_TYPE_LIST_TEST_KEY"
        val setKey: String = keyPrefix + "_TYPE_SET_TEST_KEY"
        val nonKey: String = keyPrefix + "_TYPE_NONE_TEST_KEY"

        // create some possible types
        execute("SET $stringKey value")
        execute("LPUSH $listKey value")
        execute("SADD $setKey value")

        assertThat(executeSingleStringResult("TYPE $stringKey")).isEqualTo("string")
        assertThat(executeSingleStringResult("TYPE $listKey")).isEqualTo("list")
        assertThat(executeSingleStringResult("TYPE $setKey")).isEqualTo("set")
        assertThat(executeSingleStringResult("TYPE $nonKey")).isEqualTo("none")

        delete(stringKey)
        delete(listKey)
        delete(setKey)
    }

    @Test
    @Throws(Exception::class)
    fun keys() {
        val key: String = keyPrefix + "_KEYS_1"
        val keyGlob: String = keyPrefix + "_KEYS_1*"
        createValue(key, "value")
        assertThat(executeSingleStringResult("KEYS $keyGlob")).isEqualTo(key)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun randomkey() {
        val key: String = keyPrefix + "_RANDOMKEY"
        createValue(key, "value")
        assertThat(executeSingleStringResult("RANDOMKEY")).isEqualTo(key)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun rename() {
        val oldNameKey: String = keyPrefix + "_RENAME_TEST_OLD_KEY"
        val newNameKey: String = keyPrefix + "_RENAME_TEST_NEW_KEY"

        createValue(oldNameKey, "value")
        // now we rename it, it should destroy the old key.
        execute("RENAME $oldNameKey $newNameKey")

        // a get on the old key should return null.
        assertThat(retrieveValue(oldNameKey)).isNull()

        // the new key should be defined and with out value.
        assertThat(retrieveValue(newNameKey)).isEqualTo("value")

        delete(newNameKey)
    }

    @Test
    @Throws(Exception::class)
    fun renamenx() {
        val oldNameKey: String = keyPrefix + "_RENAMENX_TEST_OLD_KEY"
        val newNameKey: String = keyPrefix + "_RENAMENX_TEST_NEW_KEY"

        // setting a test key...
        createValue(oldNameKey, "value")

        // now we rename it, it should destroy the old key and return true.
        assertThat(executeSingleBooleanResult("RENAMENX $oldNameKey $newNameKey")).isTrue()

        // a get on the old key should return null.
        assertThat(retrieveValue(oldNameKey)).isNull()

        // the new key should be defined and with out value.
        assertThat(retrieveValue(newNameKey)).isEqualTo("value")

        // now let's set the old one again
        createValue(oldNameKey, "value")

        // the new key already exists and renamenx should return false.
        assertThat(executeSingleBooleanResult("RENAMENX $oldNameKey $newNameKey")).isFalse()

        delete(oldNameKey)
        delete(newNameKey)
    }

    @Test
    @Throws(Exception::class)
    fun dbsize() {
        assertThat(executeSingleIntegerResult("DBSIZE")).isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun expire() {
        val key: String = keyPrefix + "_EXPIRE_TEST_KEY"

        createValue(key, "value")

        // set it to expire in one seconds...
        execute("EXPIRE $key 1")

        // sleep a little so Redis can remove the key in time.
        try {
            Thread.sleep(2000) // two seconds
        } catch (e: InterruptedException) {
            throw RuntimeException(e.message)
        }

        // the key should not exists anymore.
        assertThat(retrieveValue(key)).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun pexpire() {
        val key: String = keyPrefix + "_PEXPIRE_TEST_KEY"

        createValue(key, "value")

        // set it to expire in one seconds...
        execute("PEXPIRE $key 1000")

        // sleep a little so Redis can remove the key in time.
        try {
            Thread.sleep(2000) // two seconds
        } catch (e: InterruptedException) {
            throw RuntimeException(e.message)
        }

        // the key should not exists anymore.
        assertThat(retrieveValue(key)).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun expireat() {
        val key: String = keyPrefix + "_EXPIREAT_TEST_KEY"

        createValue(key, "value")

        // set it to expire in one second...
        execute("EXPIREAT $key 1293840000")

        // the key should not exist anymore.
        val actual = retrieveValue(key)
        assertThat(actual).isNullOrEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun ttl() {
        val key: String = keyPrefix + "_TTL_TEST_KEY"

        createValue(key, "value")

        execute("EXPIRE $key 10")

        val timeout = executeSingleIntegerResult("TTL $key")
        assertThat(timeout).`as`("Timeout is between 10 and 0").isBetween(0, 10)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun pttl() {
        val key: String = keyPrefix + "_PTTL_TEST_KEY"

        createValue(key, "value")

        execute("PEXPIRE $key 10000")

        val timeout = executeSingleIntegerResult("PTTL $key")
        assertThat(timeout).`as`("Timeout is between 10 and 0").isBetween(0, 10000)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun rpush() {
        val key: String = keyPrefix + "_RPUSH_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        assertThat(executeSingleStringResult("LINDEX $key 1")).isEqualTo("second")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun rpushx() {
        val key: String = keyPrefix + "_RPUSHX_TEST_KEY"
        assertThat(executeSingleIntegerResult("RPUSHX $key 1")).isEqualTo(0)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lpush() {
        val key: String = keyPrefix + "_LPUSH_TEST_KEY"
        execute("LPUSH $key first")
        execute("LPUSH $key second")
        assertThat(executeSingleStringResult("LINDEX $key 1")).isEqualTo("first")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lpushx() {
        val key: String = keyPrefix + "_LPUSHX_TEST_KEY"

        assertThat(executeSingleIntegerResult("LPUSHX $key 1")).isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun llen() {
        val key: String = keyPrefix + "_LLEN_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        assertThat(executeSingleIntegerResult("LLEN $key")).isEqualTo(2)
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lrange() {
        val key: String = keyPrefix + "_LRANGE_TEST_KEY"

        execute("LPUSH $key first")
        execute("LPUSH $key second")

        val results = executeStringResults("LRANGE $key 1 2")
        assertThat<String?>(results).hasSize(1)
        assertThat(results[0]).isEqualTo("first")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun ltrim() {
        val key: String = keyPrefix + "_LTRIM_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        execute("RPUSH $key third")

        execute("LTRIM $key 0 1")

        val results = executeStringResults("LRANGE $key 0 2")
        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo("first")
        assertThat(results[1]).isEqualTo("second")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lpop() {
        val key: String = keyPrefix + "_LPOP_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        execute("RPUSH $key third")

        val r: Array<String> = arrayOf<String>("first", "second", "third")

        for (i in r.indices) {
            assertThat(executeSingleStringResult("LPOP $key")).isEqualTo(r[i])
        }

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun rpop() {
        val key: String = keyPrefix + "_RPOP_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        execute("RPUSH $key third")

        val r: Array<String> = arrayOf<String>("third", "second", "first")

        for (i in r.indices) {
            assertThat(executeSingleStringResult("RPOP $key")).isEqualTo(r[i])
        }

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lset() {
        val key: String = keyPrefix + "_LSET_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        execute("RPUSH $key third")

        val r: Array<String> = arrayOf<String>("first", "second", "new_third")

        execute("LSET $key 2 new_third")

        for (i in r.indices) {
            assertThat(executeSingleStringResult("LPOP $key")).isEqualTo(r[i])
        }

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun lrem() {
        val key: String = keyPrefix + "_LREM_TEST_KEY"
        execute("RPUSH $key first")
        execute("RPUSH $key second")
        execute("RPUSH $key third")

        val r: Array<String> = arrayOf<String>("first", "third")

        execute("LREM $key 1 second")

        for (i in r.indices) {
            assertThat(executeSingleStringResult("LPOP $key")).isEqualTo(r[i])
        }

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun append() {
        val key: String = keyPrefix + "_APPEND"
        assertThat(executeSingleIntegerResult("APPEND $key value")).isEqualTo(5)
        assertThat(executeSingleIntegerResult("APPEND $key s")).isEqualTo(6)
        assertThat(retrieveValue(key)).isEqualTo("values")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun mset() {
        assertThat(
            executeSingleStringResult(
                ("MSET " + keyPrefix + "_MSET1 value1 "
                        + keyPrefix + "_MSET2 value2")
            )
        ).isEqualTo("OK")

        assertThat(retrieveValue(keyPrefix + "_MSET1")).isEqualTo("value1")
        assertThat(retrieveValue(keyPrefix + "_MSET2")).isEqualTo("value2")

        delete(keyPrefix + "_MSET1")
        delete(keyPrefix + "_MSET2")
    }

    @Test
    @Throws(Exception::class)
    fun getset() {
        val key: String = keyPrefix + "_GETSET"
        createValue(key, "value")
        assertThat(executeSingleStringResult("GETSET $key value1")).isEqualTo("value")
        assertThat(retrieveValue(key)).isEqualTo("value1")
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun scan() {
        val key: String = keyPrefix + "_SCAN"

        for (i in 0..29) {
            createValue(key + i, "value$i")
        }

        val results: MutableSet<String?> = HashSet<String?>(executeStringResults("SCAN 0"))

        assertThat<String?>(results).hasSize(30)

        for (i in 0..29) {
            assertThat<String?>(results).contains(key + i)
        }

        for (i in 0..29) {
            delete(key + i)
        }
    }

    @Test
    @Throws(Exception::class)
    fun bgrewriteaof() {
        assertThat(executeSingleStringResult("BGREWRITEAOF"))
            .isEqualTo("Background append only file rewriting started")
    }

    @Test
    @Throws(Exception::class)
    fun setbit() {
        val key: String = keyPrefix + "_SETBIT"

        assertThat(executeSingleIntegerResult("SETBIT $key 1 1")).isEqualTo(0)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun getbit() {
        val key: String = keyPrefix + "_GETBIT"

        assertThat(executeSingleIntegerResult("SETBIT $key 7 1")).isEqualTo(0)
        assertThat(executeSingleIntegerResult("GETBIT $key 7")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun bitcount() {
        val key: String = keyPrefix + "_BITCOUNT"

        createValue(key, "foobar")
        assertThat(executeSingleIntegerResult("BITCOUNT $key")).isEqualTo(26)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun bitpos() {
        val key: String = keyPrefix + "_BITPOS"

        createValue(key, "\"\\xff\\xf0\\x00\"")
        assertThat(executeSingleIntegerResult("BITPOS $key 0")).isEqualTo(12)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun bitop() {
        val key: String = keyPrefix + "_BITOP"

        createValue(key + 1, "foobar")
        createValue(key + 2, "abcdef")
        assertThat(
            executeSingleIntegerResult(
                "BITOP AND " + (key + 3) + " " +
                        (key + 1) + " " + (key + 2)
            )
        ).isEqualTo(6)
        assertThat(retrieveValue(key + 3)).isEqualTo("`bc`ab")
        delete(key + 1)
        delete(key + 2)
        delete(key + 3)
    }

    @Test
    @Throws(Exception::class)
    fun bitfield() {
        val key: String = keyPrefix + "_BITFIELD"

        val results = executeIntegerResults("BITFIELD $key INCRBY i5 100 1 GET u4 0")

        assertThat<Int?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo(1)
        assertThat(results[1]).isEqualTo(0)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hdel() {
        val key: String = keyPrefix + "_HDEL"

        execute("HSET $key field1 \"foo\"")
        assertThat(executeSingleIntegerResult("HDEL $key field1 \"foo\"")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("HDEL $key field1 \"foo\"")).isEqualTo(0)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hexists() {
        val key: String = keyPrefix + "_HEXISTS"

        execute("HSET $key field1 \"foo\"")
        assertThat(executeSingleIntegerResult("HEXISTS $key field1")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("HEXISTS $key field2")).isEqualTo(0)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hget() {
        val key: String = keyPrefix + "_HGET"

        execute("HSET $key field1 \"foo\"")
        assertThat(executeSingleStringResult("HGET $key field1")).isEqualTo("foo")
        assertThat(executeSingleStringResult("HGET $key field2")).isNull()

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hgetall() {
        val key: String = keyPrefix + "_HGETALL"

        execute("HSET $key field1 \"Hello\"")
        execute("HSET $key field2 \"World\"")

        //TODO: should return two rows with two columns
        val results = executeStringResults("HGETALL $key")

        assertThat<String?>(results).hasSize(4)
        assertThat(results[0]).isEqualTo("field1")
        assertThat(results[1]).isEqualTo("Hello")
        assertThat(results[2]).isEqualTo("field2")
        assertThat(results[3]).isEqualTo("World")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hincrby() {
        val key: String = keyPrefix + "_HINCRBY"

        execute("HSET $key field1 5")
        assertThat(executeSingleIntegerResult("HINCRBY $key field1 1")).isEqualTo(6)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hincrbyfloat() {
        val key: String = keyPrefix + "_HINCRBYFLOAT"

        execute("HSET $key field1 10.50")
        assertThat(executeSingleStringResult("HINCRBYFLOAT $key field1 0.1")).isEqualTo("10.6")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun dragos() {
        val results = executeStringResults("scan 0")
        println("Here")
    }

    @Test
    @Throws(Exception::class)
    fun hkeys() {
        val key: String = keyPrefix + "_HKEYS"

        execute("HSET $key field1 \"Hello\"")
        execute("HSET $key field2 \"World\"")

        val results = executeStringResults("HKEYS $key")

        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo("field1")
        assertThat(results[1]).isEqualTo("field2")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hlen() {
        val key: String = keyPrefix + "_HLEN"

        execute("HSET $key field1 \"Hello\"")
        execute("HSET $key field2 \"World\"")
        assertThat(executeSingleIntegerResult("HLEN $key")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hmget() {
        val key: String = keyPrefix + "_HMGET"

        execute("HSET $key field1 \"Hello\"")
        execute("HSET $key field2 \"World\"")
        val results = executeStringResults("HMGET $key field1 field2 nofield")

        assertThat<String?>(results).hasSize(3)
        assertThat(results[0]).isEqualTo("Hello")
        assertThat(results[1]).isEqualTo("World")
        assertThat(results[2]).isNull()

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hmset() {
        val key: String = keyPrefix + "_HMSET"

        execute("HMSET $key field1 \"Hello\" field2 \"World\"")
        assertThat(executeSingleStringResult("HGET $key field1")).isEqualTo("Hello")
        assertThat(executeSingleStringResult("HGET $key field2")).isEqualTo("World")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hscan() {
        val key: String = keyPrefix + "_HSCAN"

        execute("HMSET $key field1 \"Hello\" field2 \"World\"")

        val results = executeStringResults("HSCAN $key 0")

        //TODO: return values as multiple columns
        assertThat<String?>(results).hasSize(4)
        assertThat(results[0]).isEqualTo("field1")
        assertThat(results[1]).isEqualTo("Hello")
        assertThat(results[2]).isEqualTo("field2")
        assertThat(results[3]).isEqualTo("World")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hset() {
        val key: String = keyPrefix + "_HSET"

        assertThat(executeSingleIntegerResult("HSET $key field1 \"foo\"")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hsetnx() {
        val key: String = keyPrefix + "_HSETNX"

        assertThat(executeSingleIntegerResult("HSETNX $key field \"Hello\"")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("HSETNX $key field \"World\"")).isEqualTo(0)
        assertThat(executeSingleStringResult("HGET $key field")).isEqualTo("Hello")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hstrlen() {
        val key: String = keyPrefix + "_HSTRLN"

        execute("HSET $key field \"Hello\"")
        assertThat(executeSingleIntegerResult("HSTRLEN $key field")).isEqualTo(5)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun hvals() {
        val key: String = keyPrefix + "_HVALS"

        execute("HSET $key field1 \"Hello\"")
        execute("HSET $key field2 \"World\"")

        val results = executeStringResults("HVALS $key")

        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo("Hello")
        assertThat(results[1]).isEqualTo("World")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zadd() {
        val key: String = keyPrefix + "_ZADD"

        assertThat(executeSingleIntegerResult("ZADD $key 1 \"one\"")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zcard() {
        val key: String = keyPrefix + "_ZCARD"

        execute("ZADD $key 1 \"one\"")
        execute("ZADD $key 2 \"two\"")
        assertThat(executeSingleIntegerResult("ZCARD $key")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zcount() {
        val key: String = keyPrefix + "_ZCOUNT"

        execute("ZADD $key 1 \"one\"")
        execute("ZADD $key 2 \"two\"")
        execute("ZADD $key 3 \"three\"")
        assertThat(executeSingleIntegerResult("ZCOUNT $key (1 3")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zincrby() {
        val key: String = keyPrefix + "_ZINCRBY"

        assertThat(executeSingleIntegerResult("ZADD $key 1 \"one\"")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("ZINCRBY $key 2 \"one\"")).isEqualTo(3)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zinterstore() {
        val key: String = keyPrefix + "_ZINTERSTORE"

        execute("ZADD " + key + "1 1 \"one\"")
        execute("ZADD " + key + "2 2 \"one\"")
        assertThat(executeSingleIntegerResult("ZINTERSTORE " + key + "OUT" + " 2 " + key + "1 " + key + "2")).isEqualTo(
            1
        )

        delete(key + 1)
        delete(key + 2)
        delete(key + "OUT")
    }

    @Test
    @Throws(Exception::class)
    fun zlexcount() {
        val key: String = keyPrefix + "_ZLEXCOUNT"

        execute("ZADD $key 0 a 0 b 0 c 0 d 0 e")
        assertThat(executeSingleIntegerResult("ZLEXCOUNT $key - +")).isEqualTo(5)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrange() {
        val key: String = keyPrefix + "_ZRANGE"


        //TODO: handle WITHSCORES
        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        assertThat(executeSingleStringResult("ZRANGE $key 2 3")).isEqualTo("three")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrangebylex() {
        val key: String = keyPrefix + "_ZRANGEBYLEX"

        execute("ZADD $key 1 \"a\" 2 \"b\" 3 \"c\"")
        val results = executeStringResults("ZRANGEBYLEX $key - [b")

        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo("a")
        assertThat(results[1]).isEqualTo("b")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrangebyscore() {
        val key: String = keyPrefix + "_ZRANGEBYSCORE"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        val results = executeStringResults("ZRANGEBYSCORE $key 1 2")
        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo("one")
        assertThat(results[1]).isEqualTo("two")


        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrank() {
        val key: String = keyPrefix + "_ZRANK"

        execute("ZADD $key 1 \"a\" 1 \"b\" 2 \"c\"")
        assertThat(executeSingleIntegerResult("ZRANK $key c")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrem() {
        val key: String = keyPrefix + "_ZREM"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        assertThat(executeSingleIntegerResult("ZREM $key two")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zremrangebylex() {
        val key: String = keyPrefix + "_ZREMRANGEBYLEX"

        execute("ZADD $key 0 a 0 b 0 c 0 d 0 e")
        assertThat(executeSingleIntegerResult("ZREMRANGEBYLEX $key (a [c")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zremrangebyrank() {
        val key: String = keyPrefix + "_ZREMRANGEBYRANK"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        assertThat(executeSingleIntegerResult("ZREMRANGEBYRANK $key 1 2")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zremrangebyscore() {
        val key: String = keyPrefix + "_ZREMRANGEBYSCORE"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        assertThat(executeSingleIntegerResult("ZREMRANGEBYSCORE $key -inf (2")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrevrange() {
        val key: String = keyPrefix + "_ZREVRANGE"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        val results = executeStringResults("ZREVRANGE $key 0 -1")
        assertThat<String?>(results).hasSize(3)
        assertThat(results[0]).isEqualTo("three")
        assertThat(results[1]).isEqualTo("two")
        assertThat(results[2]).isEqualTo("one")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrevrangebylex() {
        val key: String = keyPrefix + "_ZREVRANGEBYLEX"

        execute("ZADD $key 0 a 0 b 0 c 0 d 0 e 0 f 0 g")
        val results = executeStringResults("ZREVRANGEBYLEX $key [c -")
        assertThat<String?>(results).hasSize(3)
        assertThat(results[0]).isEqualTo("c")
        assertThat(results[1]).isEqualTo("b")
        assertThat(results[2]).isEqualTo("a")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrevrangebyscore() {
        val key: String = keyPrefix + "_ZREVRANGEBYSCORE"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        val results = executeStringResults("ZREVRANGEBYSCORE $key +inf -inf")
        assertThat<String?>(results).hasSize(3)
        assertThat(results[0]).isEqualTo("three")
        assertThat(results[1]).isEqualTo("two")
        assertThat(results[2]).isEqualTo("one")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zrevrank() {
        val key: String = keyPrefix + "_ZREMRANGEBYSCORE"

        execute("ZADD $key 1 \"one\" 2 \"two\" 3 \"three\"")
        assertThat(executeSingleIntegerResult("ZREVRANK $key one")).isEqualTo(2)
        assertThat(executeSingleStringResult("ZREVRANK $key four")).isNull()

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zscan() {
        val key: String = keyPrefix + "_ZSCAN"

        execute("ZADD $key 1 \"one\" 2 \"two\"")

        val results = executeStringResults("ZSCAN $key 0")

        //TODO: return values as multiple columns
        assertThat<String?>(results).hasSize(4)
        assertThat(results[0]).isEqualTo("one")
        assertThat(results[1]).isEqualTo("1")
        assertThat(results[2]).isEqualTo("two")
        assertThat(results[3]).isEqualTo("2")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zscore() {
        val key: String = keyPrefix + "_ZSCORE"

        execute("ZADD $key 1 \"one\"")
        assertThat(executeSingleIntegerResult("ZSCORE $key one")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun zunionstore() {
        val key: String = keyPrefix + "_ZUNIONSTORE"

        execute("ZADD " + key + "1 1 \"one\"")
        execute("ZADD " + key + "2 2 \"one\"")
        assertThat(executeSingleIntegerResult("ZUNIONSTORE " + key + "OUT" + " 2 " + key + "1 " + key + "2")).isEqualTo(
            1
        )

        delete(key + 1)
        delete(key + 2)
        delete(key + "OUT")
    }

    @Test
    @Throws(Exception::class)
    fun sadd() {
        val key: String = keyPrefix + "_SADD"

        assertThat(executeSingleIntegerResult("SADD $key \"Hello\"")).isEqualTo(1)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun scard() {
        val key: String = keyPrefix + "_SCARD"

        execute("SADD $key \"Hello\"")
        execute("SADD $key \"World\"")
        assertThat(executeSingleIntegerResult("SCARD $key")).isEqualTo(2)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun sdiff() {
        val key: String = keyPrefix + "_SDIFF"

        execute("SADD " + key + "1 \"a\"")
        execute("SADD " + key + "1 \"b\"")
        execute("SADD " + key + "1 \"c\"")
        execute("SADD " + key + "2 \"c\"")
        execute("SADD " + key + "2 \"d\"")
        execute("SADD " + key + "2 \"e\"")
        val results = executeStringResults("SDIFF " + key + "1 " + key + "2")

        assertThat<String?>(results).hasSize(2)
        assertThat<String?>(results).contains("a", "b")

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun sdiffstore() {
        val key: String = keyPrefix + "_SDIFFSTORE"

        execute("SADD " + key + "1 \"a\"")
        execute("SADD " + key + "1 \"b\"")
        execute("SADD " + key + "1 \"c\"")
        execute("SADD " + key + "2 \"c\"")
        execute("SADD " + key + "2 \"d\"")
        execute("SADD " + key + "2 \"e\"")
        assertThat(executeSingleIntegerResult("SDIFFSTORE " + key + " " + key + "1 " + key + "2")).isEqualTo(2)

        delete(key)
        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun sinter() {
        val key: String = keyPrefix + "_SINTER"

        execute("SADD " + key + "1 \"a\"")
        execute("SADD " + key + "1 \"b\"")
        execute("SADD " + key + "1 \"c\"")
        execute("SADD " + key + "2 \"c\"")
        execute("SADD " + key + "2 \"d\"")
        execute("SADD " + key + "2 \"e\"")
        val results = executeStringResults("SINTER " + key + "1 " + key + "2")

        assertThat<String?>(results).hasSize(1)
        assertThat(results[0]).isEqualTo("c")

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun sinterstore() {
        val key: String = keyPrefix + "_SINTERSTORE"

        execute("SADD " + key + "1 \"a\"")
        execute("SADD " + key + "1 \"b\"")
        execute("SADD " + key + "1 \"c\"")
        execute("SADD " + key + "2 \"c\"")
        execute("SADD " + key + "2 \"d\"")
        execute("SADD " + key + "2 \"e\"")
        assertThat(executeSingleIntegerResult("SINTERSTORE " + key + " " + key + "1 " + key + "2")).isEqualTo(1)

        delete(key)
        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun sismember() {
        val key: String = keyPrefix + "_SISMEMBER"

        execute("SADD $key one")
        assertThat(executeSingleIntegerResult("SISMEMBER $key one")).isEqualTo(1)
        assertThat(executeSingleIntegerResult("SISMEMBER $key two")).isEqualTo(0)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun smembers() {
        val key: String = keyPrefix + "_SMEMBERS"

        execute("SADD $key Hello")
        execute("SADD $key World")
        val results = executeStringResults("SMEMBERS $key")

        assertThat<String?>(results).hasSize(2)
        assertThat<String?>(results).contains("Hello", "World")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun smove() {
        val key: String = keyPrefix + "_SMOVE"

        execute("SADD " + key + "1 \"one\"")
        execute("SADD " + key + "1 \"two\"")
        execute("SADD " + key + "2 \"three\"")
        assertThat(executeSingleIntegerResult("SMOVE " + key + "1 " + key + "2 two")).isEqualTo(1)

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun spop() {
        val key: String = keyPrefix + "_SPOP"

        execute("SADD $key one")
        execute("SADD $key two")
        execute("SADD $key three")

        //assertThat(executeSingleStringResult("SPOP " + key),  anyOf(is("one"), is("two"), is("three")));
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun unwatch() {
        assertThat(executeSingleStringResult("UNWATCH")).isEqualTo("OK")
    }

    @Test
    @Throws(Exception::class)
    fun watch() {
        val key: String = keyPrefix + "_WATCH"

        assertThat(executeSingleStringResult("WATCH $key")).isEqualTo("OK")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun time() {
        //TODO: convert the response into a java datetime

        val results = executeIntegerResults("TIME")
        assertThat<Int?>(results).hasSize(2)
    }

    @Test
    @Throws(Exception::class)
    fun strlen() {
        val key: String = keyPrefix + "_STRLEN"

        createValue(key, "\"Hello world\"")
        assertThat(executeSingleIntegerResult("STRLEN $key")).isEqualTo(11)

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun blpop() {
        val key: String = keyPrefix + "_BLPOP"

        execute("RPUSH $key a b c")
        val results = executeStringResults("BLPOP $key 0")
        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo(key)
        assertThat(results[1]).isEqualTo("a")


        //TODO: should be returned as a single row
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun brpop() {
        val key: String = keyPrefix + "_BRPOP"

        execute("RPUSH $key a b c")
        val results = executeStringResults("BRPOP $key 0")
        assertThat<String?>(results).hasSize(2)
        assertThat(results[0]).isEqualTo(key)
        assertThat(results[1]).isEqualTo("c")

        //TODO: should be returned as a single row
        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun rpoplpush() {
        val key: String = keyPrefix + "_RPOPLPUSH"

        execute("RPUSH " + key + "1 a b c")
        assertThat(executeSingleStringResult("RPOPLPUSH " + key + "1 " + key + "2")).isEqualTo("c")

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun brpoplpush() {
        val key: String = keyPrefix + "_BRPOPLPUSH"

        execute("RPUSH " + key + "1 a b c")
        assertThat(executeSingleStringResult("BRPOPLPUSH " + key + "1 " + key + "2 0")).isEqualTo("c")

        delete(key + 1)
        delete(key + 2)
    }

    @Test
    @Throws(Exception::class)
    fun lindex() {
        val key: String = keyPrefix + "_LINDEX"

        execute("RPUSH $key a b c")
        assertThat(executeSingleStringResult("LINDEX $key 0")).isEqualTo("a")

        delete(key)
    }

    @Test
    @Throws(Exception::class)
    fun linsert() {
        val key: String = keyPrefix + "_LINSERT"

        execute("RPUSH $key a c")
        assertThat(executeSingleIntegerResult("LINSERT $key BEFORE c a")).isEqualTo(3)

        delete(key)
    }

    @Throws(Exception::class)
    private fun execute(command: String?) {
        conn!!.createStatement().execute(command)
    }

    @Throws(Exception::class)
    private fun executeQuery(command: String?): ResultSet {
        return conn!!.createStatement().executeQuery(command)
    }

    @Throws(Exception::class)
    private fun createValue(
        key: String?,
        value: String?
    ) {
        execute("SET $key $value")
    }

    @Throws(Exception::class)
    private fun retrieveValue(key: String?): String? {
        return executeSingleStringResult("GET $key")
    }

    @Throws(Exception::class)
    private fun delete(key: String?) {
        execute("DEL $key")
    }

    @Throws(Exception::class)
    private fun executeSingleStringResult(command: String?): String? {
        val results = executeStringResults(command)
        assertThat<String?>(results).hasSize(1)
        return results[0]
    }

    @Throws(Exception::class)
    private fun executeSingleIntegerResult(command: String?): Int {
        val results = executeIntegerResults(command)
        assertThat<Int?>(results).hasSize(1)
        return results[0]!!
    }

    @Throws(Exception::class)
    private fun executeSingleBooleanResult(command: String?): Boolean? {
        val results = executeBooleanResults(command)
        assertThat<Boolean?>(results).hasSize(1)
        return results[0]
    }

    @Throws(Exception::class)
    private fun executeStringResults(command: String?): MutableList<String?> {
        return executeSingleResult<String?>(command, { rs: ResultSet?, x: Int -> rs!!.getString(x) })
    }

    @Throws(Exception::class)
    private fun executeIntegerResults(command: String?): MutableList<Int?> {
        return executeSingleResult<Int?>(command, { rs: ResultSet?, x: Int -> rs!!.getInt(x) })
    }

    @Throws(Exception::class)
    private fun executeBooleanResults(command: String?): MutableList<Boolean?> {
        return executeSingleResult<Boolean?>(command, { rs: ResultSet?, x: Int -> rs!!.getBoolean(x) })
    }

    @Throws(Exception::class)
    private fun <T> executeSingleResult(
        command: String?,
        operation: (rs: ResultSet, index: Int) -> T?
    ): MutableList<T?> {
        val result = executeQuery(command)

        val results = mutableListOf<T?>()
        while (result.next()) {
            results.add(operation.invoke(result, 0))
        }
        result.close()

        return results
    }

    init {
        conn = TestHelper.connection
        keyPrefix = TestHelper.get("keyPrefix")
    }

    companion object {
        private var conn: Connection? = null
        private var keyPrefix: String? = null

    }
}
