package com.mantushnikita.disneyherohub3.ui.hero

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import com.mantushnikita.disneyherohub3.util.toHero
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HeroViewModel(
    private val repository: HeroRepository
) : ViewModel() {

    val state = MutableLiveData<HeroState>()
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

    @SuppressLint("CheckResult")
    private fun getHeroById(id: Int) {
        state.value = HeroState.Loading
        repository.getHeroById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.isSuccessful) {
                        val hero = response.body()?.data?.toHero()
                        if (hero != null) {
                            state.value = HeroState.HeroLoaded(hero)
                            currentId = id
                        } else {
                            state.value = HeroState.Error("Hero not found")
                        }
                    }
                },
                {
                    state.value = HeroState.Error("Network Error")
                }
            )
    }

//    private fun getHeroById(id: Int) {
//        state.value = HeroState.Loading
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.getHeroById(id)
//            if (response.isSuccessful) {
//                val hero = response.body()?.data?.toHero()
//                if (hero != null) {
//                    state.postValue(HeroState.HeroLoaded(hero))
//                    currentId = id
//                } else {
//                    state.postValue(HeroState.Error("Hero not found"))
//                }
//            } else {
//                state.postValue(HeroState.Error("Network Error"))
//            }
//        }
//    }
}