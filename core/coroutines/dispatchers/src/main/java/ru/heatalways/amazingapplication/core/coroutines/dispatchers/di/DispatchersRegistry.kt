package ru.heatalways.amazingapplication.core.coroutines.dispatchers.di

import kotlinx.coroutines.Dispatchers
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.DefaultCoroutineDispatcher
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.IoCoroutineDispatcher
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.MainCoroutineDispatcher
import scout.definition.Registry

fun Registry.useDispatchersBeans() {
    singleton<MainCoroutineDispatcher> { MainCoroutineDispatcher(Dispatchers.Main) }
    singleton<IoCoroutineDispatcher> { IoCoroutineDispatcher(Dispatchers.IO) }
    singleton<DefaultCoroutineDispatcher> { DefaultCoroutineDispatcher(Dispatchers.Default) }
}