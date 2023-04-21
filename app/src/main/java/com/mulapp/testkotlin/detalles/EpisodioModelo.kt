package com.mulapp.testkotlin.detalles

import com.mulapp.testkotlin.Series.Image

data class EpisodioModelo(
    val name: String,
    val summary: String,
    val image: Image
)

data class Image(
    val original: String
)
