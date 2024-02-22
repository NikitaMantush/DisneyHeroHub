package com.mantushnikita.disneyherohub3.util

import com.mantushnikita.disneyherohub3.network.FilmContent
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.network.HeroInfo

fun HeroInfo.toHero(): Hero {
    val filmContent = arrayListOf<FilmContent>()
    if (!films.isNullOrEmpty()){
        filmContent.add(
            FilmContent("film", films)
        )
    }
    if (!shortFilms.isNullOrEmpty()){
        filmContent.add(
            FilmContent("shortFilms", shortFilms)
        )
    }
    if (!tvShows.isNullOrEmpty()){
        filmContent.add(
            FilmContent("tvShows", tvShows)
        )
    }
    if (!videoGames.isNullOrEmpty()){
        filmContent.add(
            FilmContent("videoGames", videoGames)
        )
    }
    return Hero(
        _id,
        name,
        imageUrl,
        filmContent
    )
}