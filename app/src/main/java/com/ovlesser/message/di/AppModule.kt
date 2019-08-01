package com.ovlesser.message.di

import android.content.Context
import com.ovlesser.message.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(internal var mApplication: MainApplication) {

    val appContext: Context
        @Provides
        get() = mApplication.applicationContext

    @Provides
    @Singleton
    internal fun providesApplication(): MainApplication {
        return mApplication
    }
}
