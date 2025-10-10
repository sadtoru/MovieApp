package com.tallerprogramacion.movieapp.domain.model

data class Person(
    val id: Int,
    val name: String? = null,
    val birthday: String? = null,
    val placeOfBirth: String? = null ,
    val biography: String? = null,
    val profilePath: String? = null,
    val knownFor: List<Movie> ?= null
)