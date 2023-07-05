package com.example.catalog.domain.exceptions

import com.example.core.AppException

class ProducttAlreadyExistsException(
    cause: Throwable? = null
): AppException(cause = cause)