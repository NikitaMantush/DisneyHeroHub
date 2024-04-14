package com.mantushnikita.disneyherohub3.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character")
    suspend fun getHeroes(): Response<HeroListResponse>

    @GET("character/{id}")
    suspend fun getHeroById(@Path("id") id:Int):Response<HeroResponse>
}