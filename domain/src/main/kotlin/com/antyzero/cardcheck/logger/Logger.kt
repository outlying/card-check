package com.antyzero.cardcheck.logger

interface Logger {

    fun v(tag: String, msg: String, throwable: Throwable)

    fun v(tag: String, msg: String)

    fun d(tag: String, msg: String, throwable: Throwable)

    fun d(tag: String, msg: String)

    fun i(tag: String, msg: String, throwable: Throwable)

    fun i(tag: String, msg: String)

    fun w(tag: String, msg: String, throwable: Throwable)

    fun w(tag: String, msg: String)

    fun e(tag: String, msg: String, throwable: Throwable)

    fun e(tag: String, msg: String)

    companion object : Logger {

        private val loggers: MutableList<Logger> = mutableListOf()

        fun add(logger: Logger) {
            loggers.add(logger)
        }

        override fun v(tag: String, msg: String, throwable: Throwable) {
            loggers.forEach { it.v(tag, msg, throwable) }
        }

        override fun v(tag: String, msg: String) {
            loggers.forEach { it.v(tag, msg) }
        }

        override fun d(tag: String, msg: String, throwable: Throwable) {
            loggers.forEach { it.d(tag, msg, throwable) }
        }

        override fun d(tag: String, msg: String) {
            loggers.forEach { it.d(tag, msg) }
        }

        override fun i(tag: String, msg: String, throwable: Throwable) {
            loggers.forEach { it.i(tag, msg, throwable) }
        }

        override fun i(tag: String, msg: String) {
            loggers.forEach { it.i(tag, msg) }
        }

        override fun w(tag: String, msg: String, throwable: Throwable) {
            loggers.forEach { it.w(tag, msg, throwable) }
        }

        override fun w(tag: String, msg: String) {
            loggers.forEach { it.w(tag, msg) }
        }

        override fun e(tag: String, msg: String, throwable: Throwable) {
            loggers.forEach { it.e(tag, msg, throwable) }
        }

        override fun e(tag: String, msg: String) {
            loggers.forEach { it.e(tag, msg) }
        }

    }
}