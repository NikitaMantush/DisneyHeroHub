package com.mantushnikita.disneyherohub3.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mantushnikita.disneyherohub3.repository.HeroRepository
import javax.inject.Inject

class HeroListProviderFactory @Inject constructor(
    private val repository: HeroRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeroListViewModel(repository) as T
    }
}