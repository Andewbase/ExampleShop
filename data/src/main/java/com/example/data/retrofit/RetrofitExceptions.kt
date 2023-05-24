package com.example.data.retrofit

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)