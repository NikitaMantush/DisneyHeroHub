package com.mantushnikita.disneyherohub3.network



data class HeroInfo(
    val _id: Int,
    val name: String,
    val imageUrl: String,
    val films: ArrayList<String>?,
    val shortFilms: ArrayList<String>?,
    val tvShows: ArrayList<String>?,
    val videoGames: ArrayList<String>?,
    val parkAttractions: ArrayList<String>?,
    val allies: ArrayList<String>?,
)
data class Hero(
    val _id: Int,
    val name: String,
    val imageUrl: String,
    val filmContent: ArrayList<FilmContent>
)
data class FilmContent(
    val name: String,
    val film: ArrayList<String>
)