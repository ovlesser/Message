package com.ovlesser.message

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.model.Message
import javax.inject.Inject

/**
 * Repository handling the work with message.
 */
class DataRepository @Inject constructor(private val database: AppDatabase) {

    var messages : MutableList<Message> = arrayListOf()
    var observable: MediatorLiveData<List<Message>> = MediatorLiveData()

    fun loadMessage(messageId: Int): LiveData<Message> {
        return database.messageDao().loadMessage(messageId)
    }

    fun loadAllMessage( number: String) {
        observable.addSource(database.messageDao().loadAllMessage(number)
        ) {
            if (database.databaseCreated.value != null) {
                messages.clear()
                messages.addAll(it)
                observable.postValue(it)
            }
        }
    }

    fun saveAllMessage() {
        database.insertData(messages)
    }

    fun addMessage( message: Message) {
        messages.add(message)
        observable.postValue(messages)

        getResponse(message)
    }

    private fun getResponse(message: Message) {
        val responses = listOf(
            Message("random response 1", send = false, number = message.number, id = 0),
            Message("random response 2", send = false, number = message.number, id = 0),
            Message("random response 3", send = false, number = message.number, id = 0),
            Message("random response 4", send = false, number = message.number, id = 0),
            Message("random response 5", send = false, number = message.number, id = 0),
            Message("random response 6", send = false, number = message.number, id = 0),
            Message("random response 7", send = false, number = message.number, id = 0)
        )

        Handler().postDelayed({
            val response = responses.random()
            messages.add(response)
            observable.postValue(messages)
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
