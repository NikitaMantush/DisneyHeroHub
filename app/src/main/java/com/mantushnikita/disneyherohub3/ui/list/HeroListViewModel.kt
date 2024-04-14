package com.mantushnikita.disneyherohub3.ui.list

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import com.mantushnikita.disneyherohub3.ui.hero.HeroState
import com.mantushnikita.disneyherohub3.util.toHero
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HeroListViewModel(
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

    @SuppressLint("CheckResult")
    private fun loadListHeroes() {
        state.value = HeroListState.Loading
        repository.getHeroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccessful) {
                        val heroList = response.body()?.data
                        if (heroList != null) {
                            state.value = HeroListState.HeroListLoaded(heroList)
                        } else {
                            state.value = HeroListState.Error("Hero list not found")
                        }
                    }
                },
                {
                    state.value = HeroListState.Error("Network Error")
                }
            )
    }

//    private fun loadListHeroes() {
//        state.value = HeroListState.Loading
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.getHeroes()
//            if (response.isSuccessful) {
//                val heroList = response.body()?.data
//                if (heroList != null) {
//                    state.postValue(HeroListState.HeroListLoaded(heroList))
//                } else {
//                    state.postValue(HeroListState.Error("Hero list not found"))
//                }
//            } else {
//                state.postValue(HeroListState.Error("Network Error"))
//            }
//        }
//    }
}