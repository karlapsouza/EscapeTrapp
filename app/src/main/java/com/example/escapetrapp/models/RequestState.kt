package com.example.escapetrapp.models

sealed class RequestState<out T> {
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val trowable: Throwable): RequestState<Nothing>()

}