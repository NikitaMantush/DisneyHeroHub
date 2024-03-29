package com.mantushnikita.disneyherohub3.ui.list

import com.mantushnikita.disneyherohub3.network.Hero

sealed class HeroListState {
    data object Loading : HeroListState()
    class Error(val error: String) : HeroListState()
    class HeroListLoaded(val heroList: List<Hero>) : HeroListState()
}