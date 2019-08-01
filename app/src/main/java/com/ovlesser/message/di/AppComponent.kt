package com.ovlesser.message.di

import com.ovlesser.message.MainActivity
import com.ovlesser.message.model.Message
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, RoomDbModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(message: Message)
}
