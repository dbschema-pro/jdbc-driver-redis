package com.wisecoders.jdbc.redis

import java.net.URI
import java.net.URISyntaxException
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.DriverPropertyInfo
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.util.Properties
import java.util.logging.Logger

class JdbcDriver : Driver {
    @Throws(SQLException::class)
    override fun acceptsURL(url: String): Boolean {
        return url.lowercase().startsWith(JDBC_URL.lowercase())
    }

    @Throws(SQLException::class)
    override fun connect(
        url: String,
        info: Properties
    ): Connection {
        if (!this.acceptsURL(url)) {
            throw SQLException("Invalid URL: $url")
        } else {
            // remove prefix so we can use URI parsing.
            val rawUrl = url.replaceFirst("jdbc:".toRegex(), "")
            var host = DEFAULT_HOST
            var port = DEFAULT_PORT
            var dbnb = DEFAULT_DBNB
            try {
                val uri = URI(rawUrl)

                host = uri.host ?: DEFAULT_HOST
                port = if (uri.port != -1) uri.port else DEFAULT_PORT

                dbnb = DEFAULT_PORT

                if (uri.path != null && uri.path.length > 1) {
                    dbnb = uri.path.substring(1).toInt()
                }
            } catch (e: URISyntaxException) {
                throw SQLException("Could not parse JDBC URL: $url", e)
            } catch (e: NumberFormatException) {
                throw SQLException("Could not parse JDBC URL: $url", e)
            }

            return RedisConnectionFactory.getConnection(host, port, dbnb, info)
        }
    }

    override fun getMajorVersion(): Int {
        return MAJOR_VERSION
    }

    override fun getMinorVersion(): Int {
        return MINOR_VERSION
    }

    @Throws(SQLException::class)
    override fun getPropertyInfo(
        url: String,
        info: Properties
    ): Array<DriverPropertyInfo> {
        return emptyArray()
    }

    override fun jdbcCompliant(): Boolean {
        return false
    }

    @Throws(SQLFeatureNotSupportedException::class)
    override fun getParentLogger(): Logger {
        throw SQLFeatureNotSupportedException("getParentLogger")
    }

    companion object {
        private const val JDBC_URL = "jdbc:redis:"

        private const val DEFAULT_HOST = "localhost"
        private const val DEFAULT_PORT = 6379
        private const val DEFAULT_DBNB = 0

        private const val MAJOR_VERSION = 0
        private const val MINOR_VERSION = 1

        init {
            try {
                DriverManager.registerDriver(JdbcDriver())
            } catch (e: SQLException) {
                throw RuntimeException("Can't register Redis JDBC driver", e)
            }
        }
    }
}
