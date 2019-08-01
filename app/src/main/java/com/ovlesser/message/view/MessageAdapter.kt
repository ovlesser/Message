package com.ovlesser.message.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ovlesser.message.R
import com.ovlesser.message.databinding.ListItemBinding
import com.ovlesser.message.model.Message
import com.ovlesser.message.viewModel.MessageViewModel

class MessageAdapter (
    private val viewModel: MessageViewModel)
    : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    lateinit var dataBinding: ListItemBinding

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            super.onDetachedFromRecyclerView(recyclerView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.list_item, parent, false)
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
            return ViewHolder(dataBinding.root)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val message = viewModel.messages[position]
            dataBinding.message = message
            holder.bind(message)
        }

        override fun getItemCount(): Int = viewModel.messages.size

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(message: Message) {
            }
        }
    }
