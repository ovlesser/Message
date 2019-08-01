package com.ovlesser.message.di

import com.ovlesser.message.MainActivity
import com.ovlesser.message.db.MessageDao
import com.ovlesser.message.viewModel.MessageViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, RoomDbModule::class])
interface AppComponent {
    val messageDao: MessageDao
    fun inject(viewModel: MessageViewModel)
    fun inject(activity: MainActivity)
}
