package com.mantushnikita.disneyherohub3.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.usecase.HeroListResponses
import com.mantushnikita.disneyherohub3.usecase.LoadHeroListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HeroListViewModel(
    private val loadHeroListUseCase: LoadHeroListUseCase
) : ViewModel() {

    val state = MutableStateFlow<HeroListState>(HeroListState.Loading)

    fun processAction(action: HeroListAction) {
        when (action) {
            is HeroListAction.Init -> {
                loadListHeroes()
            }
        }
    }

    private fun loadListHeroes() {
        state.value = HeroListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            loadHeroListUseCase.getHeroes().collectLatest { result ->
                when {
                    result is HeroListResponses.Success -> {
                        state.emit(
                            HeroListState.HeroListLoaded(result.heroList)
                        )
                    }
                    result is HeroListResponses.Error -> {
                        state.emit(
                            HeroListState.Error("Hero list not found")
                        )
                    }
                }
            }
        }
    }
}