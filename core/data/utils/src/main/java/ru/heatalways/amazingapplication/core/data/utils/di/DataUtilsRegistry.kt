package ru.heatalways.amazingapplication.core.data.utils.di

import ru.heatalways.amazingapplication.core.data.utils.UriToFileSaver
import scout.definition.Registry

fun Registry.useDataUtilsBeans() {
    singleton<UriToFileSaver> {
        UriToFileSaver(
            applicationContext = get()
        )
    }
}