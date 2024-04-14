package com.mantushnikita.disneyherohub3.repository

import com.mantushnikita.disneyherohub3.network.Api
import javax.inject.Inject

class HeroRepository @Inject constructor(
    private val api: Api
) {
    fun getHeroes() = api.getHeroes()

    fun getHeroById(id: Int) = api.getHeroById(id)
}