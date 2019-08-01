package com.ovlesser.message.viewModel

import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.ovlesser.message.DataRepository
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.model.Contact
import javax.inject.Inject

class ContactViewModel(val contact: Contact) : ViewModel() {
    @Inject
    lateinit var repository : DataRepository
    fun update() {
        repository.loadAllMessage(contact.number)
    }
}