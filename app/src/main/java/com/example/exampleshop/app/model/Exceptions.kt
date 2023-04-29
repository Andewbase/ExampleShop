package com.example.exampleshop.app.model

import com.example.exampleshop.app.model.field.ProductField
import com.example.exampleshop.app.model.field.SignInField
import com.example.exampleshop.app.model.field.SignUpField


open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class EmptySignUpFieldException(
    val signUpField: SignUpField
) : AppException()

class EmptySignInException(
    val signInField: SignInField
): AppException()

class EmptyProductException(
    val productField: ProductField
): AppException()


class PasswordMismatchException : AppException()

class AccountAlreadyExistsException(
    cause: Throwable
) : AppException(cause = cause)

// BackendException with statusCode=401 is usually mapped to this exception
class AuthException(
    cause: Throwable
) : AppException(cause = cause)

class InvalidCredentialsException(cause: Exception) : AppException(cause = cause)

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

// ---

internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
    try {
        return block.invoke()
    } catch (e: BackendException) {
        if (e.code == 401) {
            throw AuthException(e)
        } else {
            throw e
        }
    }
}
