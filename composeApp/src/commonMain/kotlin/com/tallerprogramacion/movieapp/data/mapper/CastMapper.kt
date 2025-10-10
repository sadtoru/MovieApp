package com.tallerprogramacion.movieapp.data.mapper

import com.tallerprogramacion.movieapp.data.core.Constants.NOT_AVAILABLE
import com.tallerprogramacion.movieapp.data.model.response.credits.CastResponse
import com.tallerprogramacion.movieapp.domain.model.Cast

object CastMapper {
    fun toDomain(castResponse: CastResponse): Cast {
        return Cast(
            id = castResponse.id,
            name = castResponse.name?: NOT_AVAILABLE,
            character = castResponse.character ?: NOT_AVAILABLE,
            profilePath = castResponse.profilePath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: "https://via.placeholder.com/500x750",

            )
    }

    fun toDomainList(castResponseList: List<CastResponse>): List<Cast> = castResponseList.map { toDomain(it) }
}