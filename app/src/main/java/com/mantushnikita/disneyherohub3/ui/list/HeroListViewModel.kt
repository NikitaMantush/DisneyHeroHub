package com.mantushnikita.disneyherohub3.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val repository: HeroRepository
) : ViewModel() {

    val state = MutableLiveData<HeroListState>()

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
            val response = repository.getHeroes()
            if (response.isSuccessful) {
                val heroList = response.body()?.data
                if (heroList != null) {
                    state.postValue(HeroListState.HeroListLoaded(heroList))
                } else {
                    state.postValue(HeroListState.Error("Hero list not found"))
                }
            } else {
                state.postValue(HeroListState.Error("Network Error"))
            }
        }
    }
}