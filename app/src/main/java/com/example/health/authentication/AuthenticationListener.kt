package com.example.health.authentication

interface AuthenticationListener {

    fun onSuccess(data:Any?=null)
    fun onLoading()
    fun onFail(message:String)
}