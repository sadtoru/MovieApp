package com.tallerprogramacion.movieapp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(platformModule() + sharedModules)
    }
}

//Contexto para Android
expect fun platformModule(): org.koin.core.module.Module