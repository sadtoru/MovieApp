package com.tallerprogramacion.movieapp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        //acá lo comento porque hay que crear completar el archivo Modules en di
        //modules(sharedModules)
    }
}