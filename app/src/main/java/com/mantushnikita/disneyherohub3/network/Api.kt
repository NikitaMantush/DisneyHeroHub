package com.mantushnikita.disneyherohub3.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character")
    fun getHeroes(): Observable<Response<HeroListResponse>>

    @GET("character/{id}")
    fun getHeroById(@Path("id") id:Int): Observable<Response<HeroResponse>>
}