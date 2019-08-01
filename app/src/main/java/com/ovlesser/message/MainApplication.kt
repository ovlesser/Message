package com.ovlesser.message

import android.app.Application
import com.ovlesser.message.di.AppComponent
import com.ovlesser.message.di.AppModule
import com.ovlesser.message.di.DaggerAppComponent
import com.ovlesser.message.di.RoomDbModule

class MainApplication : Application() {

    lateinit var mainActivityViewModelComponent: AppComponent
        private set
    lateinit var appExecutors: AppExecutors
        private set

    override fun onCreate() {
        super.onCreate()

        appExecutors = AppExecutors()
        initDagger()
    }

    private fun initDagger() {
        mainActivityViewModelComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomDbModule(RoomDbModule(this))
            .build()
    }

}
