package com.mantushnikita.disneyherohub3.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val repository: HeroRepository
) : ViewModel() {
    val listHero = MutableLiveData<List<Hero>>()

    fun loadListHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getHeroes()

            if (response.isSuccessful) {
                response.body()?.data?.let { list ->
                    listHero.postValue(list)
                }
            }
        }
    }
}