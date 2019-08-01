package com.ovlesser.message.di

import android.content.Context
import com.ovlesser.message.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(internal var application: MainApplication) {

    val appContext: Context
        @Provides
        get() = application.applicationContext

    @Provides
    @Singleton
    internal fun providesApplication(): MainApplication {
        return application
    }
}
