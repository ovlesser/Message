package com.ovlesser.message.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ovlesser.message.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message order by time desc")
    fun loadAllMessage(): LiveData<List<Message>>

    @Query("SELECT * FROM message WHERE number = :number order by id asc")
    fun loadAllMessage(number: String): LiveData<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>)

    @Query("select * from message where id = :messageId order by id asc")
    fun loadMessage(messageId: Int): LiveData<Message>

    @Query("select * from message where id = :messageId order by id asc")
    fun loadMessageSync(messageId: Int): Message
}
