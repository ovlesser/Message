package com.ovlesser.message.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ovlesser.message.R
import com.ovlesser.message.databinding.ListItemBinding
import com.ovlesser.message.model.Message
import com.ovlesser.message.viewModel.MessageViewModel
import kotlinx.android.synthetic.main.list_item.view.*

class MessageAdapter (
    private val context: Fragment,
    private val viewModel: MessageViewModel)
    : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    lateinit var dataBinding: ListItemBinding
    private var messages: MutableList<Message> = arrayListOf()
    lateinit var recyclerView: RecyclerView

    init {
        viewModel.repository.observable.observe(context,
            Observer<List<Message>> { messages ->
                if (messages == null || messages.isEmpty()) {
                    this@MessageAdapter.messages.clear()
                    notifyDataSetChanged()
                } else {
                    this@MessageAdapter.messages = messages as MutableList<Message>
                    notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(this@MessageAdapter.messages.size-1)
                }
            })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages.get(position)
        message.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = messages.size

    inner class ViewHolder(val dataBinding: ListItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(message: Message) {
            dataBinding.message = message
//            view.text.setText(message.text)
            (dataBinding.root.card.layoutParams as FrameLayout.LayoutParams).run {
                gravity = if (message.send) Gravity.END else Gravity.START
            }
        }
    }
}
