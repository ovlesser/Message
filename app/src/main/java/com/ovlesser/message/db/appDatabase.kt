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

    var mExecutors: AppExecutors? = null
    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated

    abstract fun messageDao(): MessageDao

//    fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
//        if (sInstance == null) {
//            synchronized(AppDatabase::class.java) {
//                if (sInstance == null) {
//                    sInstance = buildDatabase(context.applicationContext, executors)
//                    sInstance.mExecutors = executors
//                    sInstance.updateDatabaseCreated(context.applicationContext)
//                }
//            }
//        } else {
//            sInstance.setDatabaseCreated()
//        }
//        return sInstance
//    }
//
//    private fun buildDatabase(
//        appContext: Context,
//        executors: AppExecutors
//    ): AppDatabase {
//        return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                    sInstance.updateDatabaseCreated(appContext.applicationContext)
//                }
//            }).build()
//    }

    fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    fun insertData(messages: List<Message>) {
        mExecutors!!.diskIO().execute({ runInTransaction { messageDao().insertAll(messages) } })
    }

    companion object {

        private lateinit var sInstance: AppDatabase

        @VisibleForTesting
        val DATABASE_NAME = "message-db"
    }
}
