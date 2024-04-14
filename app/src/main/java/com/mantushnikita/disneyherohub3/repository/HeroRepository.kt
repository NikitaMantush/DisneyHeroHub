package com.mantushnikita.disneyherohub3.repository

import com.mantushnikita.disneyherohub3.network.Api
import javax.inject.Inject

class HeroRepository@Inject constructor(
    private val api: Api
) {
    suspend fun getHeroes() = api.getHeroes()
    suspend fun getHeroById(id:Int) = api.getHeroById(id)
}