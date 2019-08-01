package com.ovlesser.message

import android.app.Application
import com.ovlesser.message.di.*

class MainApplication : Application() {

    lateinit var component: AppComponent
        private set
    lateinit var executors: AppExecutors
        private set

    override fun onCreate() {
        super.onCreate()

        executors = AppExecutors()
        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomDbModule(RoomDbModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }
}
