package com.example.health.chat.adapter

import com.example.health.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_message_from.view.*

class ChatFromAdapter(private val message:String):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.msg_from.text=message
    }

    override fun getLayout(): Int {
       return R.layout.layout_message_from
    }
}