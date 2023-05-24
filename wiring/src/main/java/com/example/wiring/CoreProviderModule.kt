package com.example.wiring

import android.content.Context
import com.example.core.AppRestarter
import com.example.core.CommonUi
import com.example.core.Core
import com.example.core.CoreProvider
import com.example.core.ErrorHandler
import com.example.core.Logger
import com.example.core.Resources
import com.example.core.ScreenCommunication
import com.example.core.flow.DefaultLazyFlowSubjectFactory
import com.example.core.flow.LazyFlowSubjectFactory
import com.example.impl.ActivityRequired
import com.example.impl.DefaultCoreProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreProviderModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext context: Context,
        appRestarter: AppRestarter,
    ): CoreProvider {
        return DefaultCoreProvider(
            appContext = context,
            appRestarter = appRestarter
        )
    }

    @Provides
    @ElementsIntoSet
    fun provideActivityRequiredSet(
        commonUi: CommonUi,
        screenCommunication: ScreenCommunication,
    ): Set<@JvmSuppressWildcards ActivityRequired> {
        val set = hashSetOf<ActivityRequired>()
        if (commonUi is ActivityRequired) set.add(commonUi)
        if (screenCommunication is ActivityRequired) set.add(screenCommunication)
        return set
    }

    @Provides
    fun provideScreenCommunication(): ScreenCommunication {
        return Core.screenCommunication
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return Core.globalScope
    }

    @Provides
    fun provideCommonUi(): CommonUi {
        return Core.commonUi
    }

    @Provides
    fun provideLogger(): Logger {
        return Core.logger
    }

    @Provides
    fun provideResources(): Resources {
        return Core.resources
    }

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return Core.errorHandler
    }

    @Provides
    @Singleton
    fun provideLazyFlowSubjectFactory(): LazyFlowSubjectFactory {
        return DefaultLazyFlowSubjectFactory(
            dispatcher = Dispatchers.IO
        )
    }

}