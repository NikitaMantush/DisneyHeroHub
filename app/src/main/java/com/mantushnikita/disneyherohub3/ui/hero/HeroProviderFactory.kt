package com.mantushnikita.disneyherohub3.ui.hero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mantushnikita.disneyherohub3.usecase.LoadHeroUseCase
import javax.inject.Inject

class HeroProviderFactory @Inject constructor(
    private val loadHeroUseCase: LoadHeroUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeroViewModel(loadHeroUseCase) as T
    }
}