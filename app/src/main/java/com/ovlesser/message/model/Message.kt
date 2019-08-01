package com.ovlesser.message.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "message")
data class Message(
    var text: String,
    var send: Boolean,
    var time: Date,
    var number: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
