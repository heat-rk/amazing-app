package ru.heatalways.amazingasfuckapplication.di

import kotlinx.coroutines.MainScope
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val LongRunningCoroutineScopeQualifier = qualifier("long_running_coroutine_scope")

val coroutineScopesModule = module {
    single(LongRunningCoroutineScopeQualifier) {
        MainScope()
    }
}