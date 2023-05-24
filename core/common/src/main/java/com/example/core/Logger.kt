package com.example.core

interface Logger {

    fun log(message: String)

    fun err(exception: Throwable, message: String? = null)
}