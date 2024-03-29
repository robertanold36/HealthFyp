package com.example.health.chat.adapter

import com.example.health.R
import com.example.health.model.ChatModel
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_message_to.view.*

class ChatToAdapter(private val chat:ChatModel):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.msg_to.text=chat.message
    }

    override fun getLayout(): Int {
        return R.layout.layout_message_to
    }
}