package com.mantushnikita.disneyherohub3.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mantushnikita.disneyherohub3.usecase.LoadHeroListUseCase
import javax.inject.Inject

class HeroListProviderFactory @Inject constructor(
    private val loadHeroListUseCase: LoadHeroListUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeroListViewModel(loadHeroListUseCase) as T
    }
}