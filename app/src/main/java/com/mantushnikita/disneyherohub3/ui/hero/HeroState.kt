package com.mantushnikita.disneyherohub3.ui.hero

import com.mantushnikita.disneyherohub3.network.Hero

sealed class HeroState {
    data object Loading : HeroState()
    class Error(val error: String) : HeroState()
    class HeroLoaded(val hero: Hero) : HeroState()
}