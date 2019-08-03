package com.ovlesser.message.di

import com.ovlesser.message.DataRepository
import com.ovlesser.message.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ RoomDbModule::class ])
class RepositoryModule {
    @Provides
    @Singleton
    fun getRepository(database: AppDatabase): DataRepository {
        return DataRepository.getInstance(database)
    }
}
