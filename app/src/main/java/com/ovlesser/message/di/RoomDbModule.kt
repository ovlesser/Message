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
class RoomDbModule(mApplication: MainApplication) {

    init {
        //        sInstance = Room.databaseBuilder(mApplication, AppDatabase.class, DATABASE_NAME).build();
        sInstance = getInstance(mApplication, mApplication.appExecutors)
    }

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDatabase {
        return sInstance
    }

    @Singleton
    @Provides
    internal fun providesMessageDao(messageDB: AppDatabase): MessageDao {
        return messageDB.messageDao()
    }

    companion object {

        private lateinit var sInstance: AppDatabase

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
            if (!::sInstance.isInitialized) {
                synchronized(AppDatabase::class.java) {
                    sInstance = buildDatabase(context.applicationContext, executors)
                    sInstance.mExecutors = executors
                    sInstance.updateDatabaseCreated(context.applicationContext)
                }
            } else {
                sInstance.setDatabaseCreated()
            }
            return sInstance
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(
            appContext: Context,
            executors: AppExecutors
        ): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        sInstance.updateDatabaseCreated(appContext.applicationContext)
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
