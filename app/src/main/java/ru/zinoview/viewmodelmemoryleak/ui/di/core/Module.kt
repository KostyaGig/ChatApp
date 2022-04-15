package ru.zinoview.viewmodelmemoryleak.ui.di.core

import org.koin.dsl.module.Module

interface Module {

    fun add(modules: MutableList<Module>)
}