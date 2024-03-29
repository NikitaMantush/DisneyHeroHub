package com.mantushnikita.disneyherohub3.ui.hero

sealed class HeroAction {
    class LoadById(val heroId: Int) : HeroAction()
    data object Reload : HeroAction()
}