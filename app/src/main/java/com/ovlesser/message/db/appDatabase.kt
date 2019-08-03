package com.ovlesser.message.db

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ovlesser.message.AppExecutors
import com.ovlesser.message.model.Message
import com.ovlesser.message.utils.DateConverter

@Database(entities = [Message::class ], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase() : RoomDatabase() {

    var executors: AppExecutors? = null
    private val isDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = isDatabaseCreated

    abstract fun messageDao(): MessageDao

    fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    fun setDatabaseCreated() {
        isDatabaseCreated.postValue(true)
    }

    fun insertData(messages: List<Message>) {
        executors!!.diskIO().execute {
            runInTransaction {
                messageDao().insertAll(messages)
            }
        }
    }

    companion object {

        private lateinit var instance: AppDatabase

        @VisibleForTesting
        val DATABASE_NAME = "message-db"

//        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
//            if (!::instance.isInitialized) {
//                synchronized(AppDatabase::class.java) {
//                    if (!::instance.isInitialized) {
//                        instance = buildDatabase(context.applicationContext, executors)
//                        instance.executors = executors
//                        instance.updateDatabaseCreated(context.applicationContext)
//                    }
//                }
//            } else {
//                instance.setDatabaseCreated()
//            }
//            return instance
//        }
//
//        private fun buildDatabase( context: Context, executors: AppExecutors
//        ): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        instance.updateDatabaseCreated(context.applicationContext)
//                    }
//
//                    override fun onOpen(db: SupportSQLiteDatabase) {
//                        super.onOpen(db)
//                    }
//                }).build()
//        }

    }
}
