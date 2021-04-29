package com.example.health

import kotlin.math.roundToInt

fun main(){
    val b:Long=21
    val c:Long=10
    val a= (b.toDouble()/c.toDouble()).roundToInt()
    print(a)
}