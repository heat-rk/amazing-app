package ru.heatalways.amazingasfuckapplication.common.utils

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.requireLongArg(key: String) = requireNotNull(arguments?.getLong(key))

fun NavBackStackEntry.requireStringArg(key: String) = requireNotNull(arguments?.getString(key))