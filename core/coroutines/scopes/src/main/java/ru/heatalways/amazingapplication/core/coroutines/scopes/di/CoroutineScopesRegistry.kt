package ru.heatalways.amazingapplication.core.coroutines.scopes.di

import kotlinx.coroutines.MainScope
import ru.heatalways.amazingapplication.core.coroutines.scopes.LongRunningCoroutineScope
import scout.definition.Registry

fun Registry.useCoroutineScopesBeans() {
    singleton<LongRunningCoroutineScope> { LongRunningCoroutineScope(MainScope()) }
}