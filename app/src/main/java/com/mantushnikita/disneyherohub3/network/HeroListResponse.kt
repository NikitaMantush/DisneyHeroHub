package com.mantushnikita.disneyherohub3.network

data class HeroListResponse(
    val `data`: List<Hero>,
    val info: Info
)