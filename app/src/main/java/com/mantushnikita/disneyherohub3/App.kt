package com.mantushnikita.disneyherohub3

import android.app.Application
import com.mantushnikita.disneyherohub3.di.ApplicationComponent
import com.mantushnikita.disneyherohub3.di.DaggerApplicationComponent

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
    }

    companion object{
        lateinit var appComponent: ApplicationComponent
    }

}