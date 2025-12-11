package com.wisecoders.jdbc.redis

import java.sql.ResultSetMetaData
import java.sql.Types
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class RedisResultSetMetaDataTest : BaseTest(), WithAssertions {
    private val metadata: ResultSetMetaData = RedisResultSetMetaData()

    @Test
    @Throws(Exception::class)
    fun checkValues() {
        assertThat(metadata.getColumnCount()).isEqualTo(1)
        assertThat(metadata.isAutoIncrement(0)).isFalse()
        assertThat(metadata.isCaseSensitive(0)).isTrue()
        assertThat(metadata.isSearchable(0)).isFalse()
        assertThat(metadata.isCurrency(0)).isFalse()
        assertThat(metadata.isNullable(0)).isEqualTo(ResultSetMetaData.columnNoNulls)
        assertThat(metadata.isSigned(0)).isFalse()
        assertThat(metadata.getColumnDisplaySize(0)).isEqualTo(1024)
        assertThat(metadata.getColumnLabel(0)).isEqualTo("")
        assertThat(metadata.getColumnName(0)).isEqualTo("")
        assertThat(metadata.getSchemaName(0)).isEqualTo("")
        assertThat(metadata.getPrecision(0)).isEqualTo(1024)
        assertThat(metadata.getScale(0)).isEqualTo(0)
        assertThat(metadata.getTableName(0)).isEqualTo("")
        assertThat(metadata.getCatalogName(0)).isEqualTo("")
        assertThat(metadata.getColumnType(0)).isEqualTo(Types.NVARCHAR)
        assertThat(metadata.getColumnTypeName(0)).isEqualTo("String")
        assertThat(metadata.isReadOnly(0)).isTrue()
        assertThat(metadata.isWritable(0)).isFalse()
        assertThat(metadata.isDefinitelyWritable(0)).isFalse()
        assertThat(metadata.getColumnClassName(0)).isEqualTo("java.lang.String")
    }

    @Test
    @Throws(Exception::class)
    fun validateUnimplementedMethods() {
        assertNotSupported { metadata.unwrap<Any?>(null) }
        assertNotSupported { metadata.isWrapperFor(null) }

    }
}
