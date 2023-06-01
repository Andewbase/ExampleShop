package com.example.sign_up.domain.exceptions

import com.example.core.AppException
import com.example.sign_up.domain.entities.SignUpField

class EmptyFieldException(
    val field: SignUpField
) : AppException()