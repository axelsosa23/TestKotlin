package com.mulapp.testkotlin.Series

import com.mulapp.testkotlin.detalles.Image

data class SerieModelo(
    val show: ShowDetails
)

data class ShowDetails(
    val id: Int,
    val name: String,
    val summary: String,
    val image: Image,
    val language: String,
    val rating: Rating,
)

data class Rating(
    val average: Double?
)

data class Image(
    val original: String
)
