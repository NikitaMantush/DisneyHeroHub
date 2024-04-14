package com.mantushnikita.disneyherohub3.usecase

import com.mantushnikita.disneyherohub3.model.ApiError
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadHeroListUseCase @Inject constructor(
    private val repository: HeroRepository
) {
    suspend fun getHeroes(): Flow<HeroListResponses> = flow {
        HeroResponses.Loading(true)
        val response = repository.getHeroes()
        if (response.isSuccessful) {
            response.body()?.data?.run {
                emit(HeroListResponses.Success(this))
            }
        } else {
            if (response.code() == 404) {
                emit(HeroListResponses.Error(ApiError.Error404))
            }
        }
        HeroListResponses.Loading(false)
    }

}
sealed class HeroListResponses {
    class Success(val heroList: List<Hero>) : HeroListResponses()
    class Error(val error: ApiError) : HeroListResponses()
    class Loading(val isLoading: Boolean) : HeroListResponses()

}