package com.example.impl

import android.content.Context
import com.example.core.AppRestarter
import com.example.core.CommonUi
import com.example.core.CoreProvider
import com.example.core.ErrorHandler
import com.example.core.Logger
import com.example.core.Resources
import com.example.core.ScreenCommunication
import kotlinx.coroutines.CoroutineScope

class DefaultCoreProvider(
    private val appContext: Context,
    override val appRestarter: AppRestarter,
    override val commonUi: CommonUi = AndroidCommonUi(appContext),
    override val resources: Resources = AndroidResources(appContext),
    override val screenCommunication: ScreenCommunication = DefaultScreenCommunication(),
    override val globalScope: CoroutineScope = createDefaultGlobalScope(),
    override val logger: Logger = AndroidLogger(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        logger, commonUi, resources, appRestarter, globalScope
    )
) : CoreProvider