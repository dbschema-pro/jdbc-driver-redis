package com.wisecoders.jdbc.redis

class RedisParseException : Exception {
    constructor()
    constructor(msg: String?) : super(msg)

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }
}
