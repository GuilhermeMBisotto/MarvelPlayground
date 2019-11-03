package com.guilhermembisotto.marvelplayground

import android.app.Application
import com.guilhermembisotto.data.di.getDataModules
import com.guilhermembisotto.data.di.getDataProperties
import com.guilhermembisotto.marvelplayground.ui.characterdetail.di.getCharacterDetailModules
import com.guilhermembisotto.marvelplayground.ui.main.di.getMainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@MarvelApplication)
            properties(getProperties())
            modules(getModules())
        }
    }

    private fun getModules(): MutableList<Module> {
        val modules: MutableList<Module> = arrayListOf()
        modules.addAll(getDataModules())
        modules.addAll(getMainModules())
        modules.addAll(getCharacterDetailModules())
        return modules
    }

    private fun getProperties(): Map<String, Any> {
        val properties: MutableMap<String, Any> = mutableMapOf()
        properties.putAll(getDataProperties())
        return properties
    }
}