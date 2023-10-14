package ru.heatalways.amazingapplication.feature.mirror.compose_impl.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.heatalways.amazingapplication.feature.mirror.compose_impl.ui.MirrorViewModel
import scout.Scope
import scout.scope

private var _mirrorScope: Scope? = null
internal val mirrorScope get() = requireNotNull(_mirrorScope)

fun Scope.includeMirrorScope() {
    _mirrorScope = scope("mirror_scope") {
        dependsOn(this@includeMirrorScope)

        singleton<MirrorViewModelFactory> {
            MirrorViewModelFactory(
                viewModelFactory {
                    initializer {
                        MirrorViewModel(
                            router = get(),
                        )
                    }
                }
            )
        }
    }
}