package com.example.health.model


 class ChatModel(
    var message: String?="",
    var senderId: String?="",
    var receiverId: String?="",
    var read:Boolean?=false,
    var date: String?=""
)