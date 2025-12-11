package com.wisecoders.jdbc.redis

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.ResourceBundle

object TestHelper  {
    private const val propFile = "config"
    private val bundle: ResourceBundle = ResourceBundle.getBundle(propFile)

    fun get(propName: String): String {
        return bundle.getString(propName)
    }

    val connectionString: String
        get() {
            val connUrl = "jdbc:redis://" +
                    get("host") + ":" +
                    get("port") + "/" +
                    get("dbnb")
            return connUrl
        }

    val connection: Connection?
        get() {
            try {
                return DriverManager.getConnection(this.connectionString)
            } catch (e: SQLException) {
                throw RuntimeException(e.message)
            }
        }

}
