package com.mantushnikita.disneyherohub3.di

import com.mantushnikita.disneyherohub3.ui.hero.HeroFragment
import com.mantushnikita.disneyherohub3.ui.list.HeroListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(fragment: HeroFragment)
    fun inject(fragment: HeroListFragment)
}