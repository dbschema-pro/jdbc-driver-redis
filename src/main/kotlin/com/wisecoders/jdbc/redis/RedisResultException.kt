package com.wisecoders.jdbc.redis

class RedisResultException : Exception {
    constructor()
    constructor(msg: String?) : super(msg)

    companion object {
        private const val serialVersionUID = 1L
    }
}
