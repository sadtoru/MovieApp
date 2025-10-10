package com.tallerprogramacion.movieapp.data.model.response.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CastResponse(
    val id: Int,
    @SerialName("name") val name: String? = null,
    @SerialName("character") val character: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
)