package com.example.health.model

import java.util.*

data class ChatModel(
    val message: String,
    val senderId: String,
    val receiverId: String,
    val isRead: Boolean,
    val date: Calendar
)
