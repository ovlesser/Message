package com.ovlesser.message.viewModel

import androidx.lifecycle.ViewModel
import com.ovlesser.message.DataRepository
import com.ovlesser.message.model.Message
import java.util.*

class MessageViewModel( val repository: DataRepository) : ViewModel() {

    fun add(message: Message): Unit {
        repository.addMessage(message)
    }

    fun add(text: String, send: Boolean, number: String): Unit {
        add(Message(text, send, Date(), number, 0))
    }

    fun update( number: String) {
        repository.saveAllMessage()
        repository.loadAllMessage(number)
    }
}