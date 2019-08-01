package com.ovlesser.message.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ovlesser.message.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE number = :number order by time desc")
    fun loadAllMessage(number: String): LiveData<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message)

    @Query("select * from message where id = :messageId order by time desc")
    fun loadMessage(messageId: Int): LiveData<Message>

    @Query("select * from message where id = :messageId order by time desc")
    fun loadMessageSync(messageId: Int): Message
}
