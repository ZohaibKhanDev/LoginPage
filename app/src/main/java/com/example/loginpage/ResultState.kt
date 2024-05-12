package com.example.loginpage

sealed class ResultState <out T> {
    object Loading:ResultState<Nothing>()
    data class Success<T>(val response:T):ResultState<T>()
    data class Error(val error:String):ResultState<Nothing>()
}