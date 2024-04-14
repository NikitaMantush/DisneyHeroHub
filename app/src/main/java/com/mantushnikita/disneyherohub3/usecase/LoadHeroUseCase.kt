package com.mantushnikita.disneyherohub3.usecase

import com.mantushnikita.disneyherohub3.model.ApiError
import com.mantushnikita.disneyherohub3.network.Api
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import com.mantushnikita.disneyherohub3.util.toHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadHeroUseCase @Inject constructor(
    private val repository: HeroRepository
) {
    suspend fun getHeroById(id: Int): Flow<HeroResponses> = flow {
        HeroResponses.Loading(true)
        val response = repository.getHeroById(id)
        if (response.isSuccessful) {
            response.body()?.data?.toHero()?.run {
                emit(HeroResponses.Success(this))
            }
        } else {
            if (response.code() == 404) {
                emit(HeroResponses.Error(ApiError.Error404))
            }
        }
        HeroResponses.Loading(false)
    }
}

sealed class HeroResponses {
    class Success(val hero: Hero) : HeroResponses()
    class Error(val error: ApiError) : HeroResponses()
    class Loading(val isLoading: Boolean) : HeroResponses()

}