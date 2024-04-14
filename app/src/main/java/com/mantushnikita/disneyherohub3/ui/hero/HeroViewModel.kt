package com.mantushnikita.disneyherohub3.ui.hero

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.usecase.HeroResponses
import com.mantushnikita.disneyherohub3.usecase.LoadHeroUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HeroViewModel(
    private val loadHeroUseCase: LoadHeroUseCase
) : ViewModel() {

    val state = MutableStateFlow<HeroState>(HeroState.Loading)
    private var currentId: Int? = null


    fun processAction(action: HeroAction) {
        when (action) {
            is HeroAction.LoadById -> {
                getHeroById(action.heroId)
            }

            is HeroAction.Reload -> {
                currentId?.let { heroId ->
                    getHeroById(heroId)
                }
            }
        }
    }

//    @SuppressLint("CheckResult")
//    private fun getHeroById(id: Int) {
//        state.value = HeroState.Loading
//        repository.getHeroById(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { response ->
//                    if (response.isSuccessful) {
//                        val hero = response.body()?.data?.toHero()
//                        if (hero != null) {
//                            state.value = HeroState.HeroLoaded(hero)
//                            currentId = id
//                        } else {
//                            state.value = HeroState.Error("Hero not found")
//                        }
//                    }
//                },
//                {
//                    state.value = HeroState.Error("Network Error")
//                }
//            )
//    }

    private fun getHeroById(id: Int) {
        state.value = HeroState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            loadHeroUseCase.getHeroById(id).collectLatest { result ->
                when {
                    result is HeroResponses.Success -> {
                        state.emit(
                            HeroState.HeroLoaded(result.hero)
                        )
                        currentId = id
                    }
                    result is HeroResponses.Error -> {

                    }
                }
            }
        }
    }
}