package com.example.catalog.domain.exceptions

import com.example.catalog.domain.entities.CreateProductField
import com.example.core.AppException

class EmptyFieldException(
    val field: CreateProductField
): AppException()