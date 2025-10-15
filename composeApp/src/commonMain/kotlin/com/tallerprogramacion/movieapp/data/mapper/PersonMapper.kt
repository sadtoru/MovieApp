package com.tallerprogramacion.movieapp.data.mapper

import com.tallerprogramacion.movieapp.data.core.Constants.NOT_AVAILABLE
import com.tallerprogramacion.movieapp.data.model.response.person.PersonResponse
import com.tallerprogramacion.movieapp.domain.model.Person

object PersonMapper {
    fun toDomain(personResponse: PersonResponse): Person {
        return Person(
            id = personResponse.id,
            name = personResponse.name ?: NOT_AVAILABLE,
            birthday = personResponse.birthday ?: NOT_AVAILABLE,
            placeOfBirth = personResponse.placeOfBirth ?: NOT_AVAILABLE,
            biography = personResponse.biography ?: NOT_AVAILABLE,
            profilePath = personResponse.profilePath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: "https://via.placeholder.com/500x750",
            knownFor = MovieMapper.toDomainMoviesForActor(personResponse.combinedCredits?.cast ?: emptyList())
        )
    }
}