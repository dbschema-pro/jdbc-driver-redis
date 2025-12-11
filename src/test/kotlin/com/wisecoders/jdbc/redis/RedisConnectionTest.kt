package com.wisecoders.jdbc.redis

import java.sql.Connection
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class RedisConnectionTest : BaseTest(), WithAssertions {
    private var connection: Connection? = null

    @BeforeEach
    @Throws(Exception::class)
    fun connect() {
        connection = TestHelper.connection
    }

    @AfterEach
    @Throws(Exception::class)
    fun quit() {
        connection!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun autoCommit() {
        connection!!.setAutoCommit(false)
        assertThat(connection!!.getAutoCommit()).isFalse()
        connection!!.setAutoCommit(true)
        assertThat(connection!!.getAutoCommit()).isTrue()
    }

    //@Disabled("failing test")
    @Test
    @Throws(Exception::class)
    fun validateUnimplementedMethods() {
        assertNotSupported{ connection!!.createArrayOf(null, null) }
        assertNotSupported{ connection!!.createBlob() }
        assertNotSupported{ connection!!.createClob() }
        assertNotSupported{ connection!!.createNClob() }
        assertNotSupported{ connection!!.createSQLXML() }
        assertNotSupported{ connection!!.createStatement(0, 0) }
        assertNotSupported{ connection!!.createStatement(0, 0, 0) }
        assertNotSupported{ connection!!.createStruct(null, null) }
        assertNotSupported{ connection!!.getClientInfo() }
        assertNotSupported{ connection!!.getClientInfo(null) }
        assertNotSupported{ connection!!.getHoldability() }
        assertNotSupported{ connection!!.getTypeMap() }
        assertNotSupported{ connection!!.nativeSQL(null) }
        assertNotSupported{ connection!!.prepareCall(null, 0, 0) }
        assertNotSupported{ connection!!.prepareCall(null, 0, 0, 0) }
        assertNotSupported{ connection!!.prepareCall(null) }
        assertNotSupported{ connection!!.prepareStatement(null, 0) }
        assertNotSupported{ connection!!.prepareStatement(null, null as IntArray?) }
        assertNotSupported{ connection!!.prepareStatement(null, null as Array<String?>?) }
        assertNotSupported{ connection!!.prepareStatement(null, 0, 0) }
        assertNotSupported{ connection!!.prepareStatement(null, 0, 0, 0) }
        assertNotSupported{ connection!!.releaseSavepoint(null) }
        assertNotSupported{ connection!!.rollback() }
        assertNotSupported{ connection!!.rollback(null) }
        assertNotSupported{ connection!!.setCatalog(null) }
        assertNotSupported{ connection!!.setHoldability(0) }
        assertNotSupported{ connection!!.setReadOnly(false) }
        assertNotSupported{ connection!!.setSavepoint() }
        assertNotSupported{ connection!!.setSavepoint(null) }
        assertNotSupported{ connection!!.setTransactionIsolation(0) }
        assertNotSupported{ connection!!.setSchema(null) }
        assertNotSupported{ connection!!.getSchema() }
        assertNotSupported{ connection!!.abort(null) }
        assertNotSupported{ connection!!.setNetworkTimeout(null, 0) }
        assertNotSupported{ connection!!.getNetworkTimeout() }
        assertNotSupported{ connection!!.isWrapperFor(null) }
        assertNotSupported{ connection!!.unwrap<Any?>(null) }
    }
}
