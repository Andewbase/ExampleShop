package com.example.sign_up.domain.exceptions

import com.example.core.AppException

class AccountAlreadyExistsException(
    cause: Throwable? = null
) : AppException(cause = cause)