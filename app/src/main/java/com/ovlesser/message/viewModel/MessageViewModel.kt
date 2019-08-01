package com.ovlesser.message.viewModel

import androidx.lifecycle.ViewModel
import com.ovlesser.message.DataRepository
import com.ovlesser.message.model.Message
import java.util.*
import javax.inject.Inject

class MessageViewModel( val messages : MutableList<Message>) : ViewModel() {
    @Inject
    lateinit var repository: DataRepository

    fun add(message: Message): Unit {
        messages.add(message)
        repository.addMessage(message)
    }

    fun add(text: String, send: Boolean, number: String): Unit {
        add(Message(text, send, Date(), number))
    }
}