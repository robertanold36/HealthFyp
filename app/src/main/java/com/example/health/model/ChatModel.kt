package com.example.health.model

import java.util.*


class ChatModel(
    var message: String?="",
    var senderId: String?="",
    var receiverId: String?="",
    var read:Boolean?=false,
    var date: Date=Calendar.getInstance().time
)