package com.example.moviesapp

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String
)
