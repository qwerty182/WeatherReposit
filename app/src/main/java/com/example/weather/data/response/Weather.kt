package com.example.weather.data.response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)