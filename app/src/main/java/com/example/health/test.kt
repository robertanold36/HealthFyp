package com.example.health


fun main(){


    val test1:((Int)->Boolean)= { it%2==0 }
    fun data(item: (Int) -> Boolean) {
       print(item(2))
    }

    fun test(x:Int): Boolean {
        return ((x%2)==0)
    }

    val result=data { test(10) }
    print(test1.invoke(10))

}
