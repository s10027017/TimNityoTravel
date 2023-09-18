package com.example.tim.nityo.travel.data.model

sealed class ApiResult<out R> {

    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error (val status: Int) : ApiResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$status]"
        }
    }
}