package com.example.tim.nityo.travel.data.model

data class NityoResponse<out T>(
    val total: Int,
    val data: T)