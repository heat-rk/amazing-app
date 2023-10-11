package ru.heatalways.amazingapplication.core.coroutines.dispatchers

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val IoDispatcherQualifier = qualifier("io_dispatcher")
val MainDispatcherQualifier = qualifier("main_dispatcher")
val DefaultDispatcherQualifier = qualifier("default_dispatcher")

val dispatchersModule = module {
    single(IoDispatcherQualifier) { Dispatchers.IO }
    single(MainDispatcherQualifier) { Dispatchers.Main }
    single(DefaultDispatcherQualifier) { Dispatchers.Default }
}