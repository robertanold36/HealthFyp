package com.example.health.tracker.listener

interface HealthTrackingEventListener {
    fun onSuccess()
    fun onFail(message:String)
    fun onLoading()
}