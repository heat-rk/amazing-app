package ru.heatalways.amazingapplication.feature.sharing.android_impl.di

import ru.heatalways.amazingapplication.feature.sharing.android_impl.AndroidSharing
import ru.heatalways.amazingapplication.feature.sharing.api.Sharing
import scout.definition.Registry

fun Registry.useAndroidSharingBeans() {
    reusable<Sharing> {
        AndroidSharing(
            context = get()
        )
    }
}