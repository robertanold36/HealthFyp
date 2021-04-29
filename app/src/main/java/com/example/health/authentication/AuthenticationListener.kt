package com.example.health.authentication

interface AuthenticationListener {

    fun onSuccess()
    fun onLoading()
    fun onFail(message:String)
}