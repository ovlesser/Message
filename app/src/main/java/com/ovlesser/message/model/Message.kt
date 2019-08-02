package com.ovlesser.message.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "message")
data class Message(
    var text: String,
    var send: Boolean = true,
    var time: Date = Date(),
    var number: String = "1111",
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {

}
