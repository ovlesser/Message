package com.ovlesser.message.di

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ovlesser.message.AppExecutors
import com.ovlesser.message.MainApplication
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.db.AppDatabase.Companion.DATABASE_NAME
import com.ovlesser.message.db.MessageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RoomDbModule(application: MainApplication) {

    init {
//        instance = Room.databaseBuilder(application, AppDatabase.class, DATABASE_NAME).build();
        instance = getInstance(application, application.executors)
    }

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDatabase {
        return instance
    }

    @Singleton
    @Provides
    internal fun providesMessageDao(messageDB: AppDatabase): MessageDao {
        return messageDB.messageDao()
    }

    companion object {

        private lateinit var instance: AppDatabase

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(AppDatabase::class.java) {
                    instance = buildDatabase(context.applicationContext, executors)
                    instance.executors = executors
                    instance.updateDatabaseCreated(context.applicationContext)
                }
            } else {
                instance.setDatabaseCreated()
            }
            return instance
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(
            context: Context,
            executors: AppExecutors
        ): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        instance.updateDatabaseCreated(context.applicationContext)
                    }
                })
                .build()
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }

        }
    }
}
