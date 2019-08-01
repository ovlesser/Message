package com.ovlesser.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.model.Message
import java.util.*
import javax.inject.Inject

/**
 * Repository handling the work with message.
 */
class DataRepository @Inject
constructor(private val mDatabase: AppDatabase) {
    private val mObservableMessage: MediatorLiveData<List<Message>> = MediatorLiveData()

    /**
     * Get the list of traffic from the database and get notified when the data changes.
     */
    val message: LiveData<List<Message>>
        get() = mObservableMessage

    init {
    }

    fun loadMessage(messageId: Int): LiveData<Message> {
        return mDatabase.messageDao().loadMessage(messageId)
    }

    fun loadAllMessage( number: String) {
        mObservableMessage.addSource(mDatabase.messageDao().loadAllMessage( number)
        ) { messages ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableMessage.postValue(messages)
            }
        }
    }

    fun addMessage( message: Message) {
        mDatabase.messageDao().insert(message)
    }

    companion object {

        private lateinit var sInstance: DataRepository

        fun getInstance(database: AppDatabase): DataRepository {
            if (!::sInstance.isInitialized) {
                synchronized(DataRepository::class.java) {
                    sInstance = DataRepository(database)
                }
            }
            return sInstance
        }
    }
}
