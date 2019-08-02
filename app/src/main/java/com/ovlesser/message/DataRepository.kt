package com.ovlesser.message

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.model.Message
import java.util.*

/**
 * Repository handling the work with message.
 */
class DataRepository(private val database: AppDatabase) {
    var messages : MutableList<Message> = arrayListOf()
    var observable: MediatorLiveData<List<Message>> = MediatorLiveData()

    fun loadMessage(messageId: Int): LiveData<Message> {
        return database.messageDao().loadMessage(messageId)
    }

    fun loadAllMessage( number: String) {
        observable.addSource(database.messageDao().loadAllMessage(number)
        ) {
            if (database.databaseCreated.value != null) {
                messages.addAll(it)
                observable.postValue(null)
                observable.postValue(it)
            }
        }
    }

    fun saveAllMessage() {
        database.insertData(messages)
    }

    fun addMessage( message: Message) {
        messages.add(message)
        observable.postValue(arrayListOf(message))

        Handler().postDelayed({
            val response = Message("re: ${message.text}", send = false, id = 0)
            observable.postValue(arrayListOf(response))
        }, 500)
    }

    companion object {

        private lateinit var instance: DataRepository

        fun getInstance(database: AppDatabase): DataRepository {
            if (!::instance.isInitialized) {
                synchronized(DataRepository::class.java) {
                    instance = DataRepository(database)
                }
            }
            return instance
        }
    }
}
