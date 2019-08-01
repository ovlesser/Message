package com.ovlesser.message.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ovlesser.message.DataRepository
import com.ovlesser.message.R
import com.ovlesser.message.databinding.FragmentMessageBinding
import com.ovlesser.message.model.Message
import com.ovlesser.message.viewModel.ContactViewModel
import com.ovlesser.message.viewModel.MessageViewModel
import kotlinx.android.synthetic.main.fragment_message.*
import javax.inject.Inject

class MessageFragment : Fragment() {
    @Inject
    lateinit var dataRepository : DataRepository
    lateinit var messageViewModel : MessageViewModel
    private lateinit var contactViewModel: ContactViewModel
    lateinit var dataBinding: FragmentMessageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        messageViewModel = activity?.run {
//            ViewModelProviders.of(this).get(MessageViewModel::class.java)
//        } ?: throw Exception("Invalid Activity")
        messageViewModel = MessageViewModel(dataRepository.message.value as MutableList<Message>)
        contactViewModel = activity?.run {
            ViewModelProviders.of(this).get(ContactViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list?.run {
            adapter = MessageAdapter(messageViewModel)
        }
        dataBinding.contactViewModel = contactViewModel
        dataBinding.messageViewModel = messageViewModel

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false)
        return dataBinding.root
    }
}