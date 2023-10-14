package ru.heatalways.amazingapplication.di

import android.content.Context
import ru.heatalways.amazingapplication.AmazingApplication
import ru.heatalways.amazingapplication.core.coroutines.dispatchers.di.useDispatchersBeans
import ru.heatalways.amazingapplication.core.coroutines.scopes.di.useCoroutineScopesBeans
import ru.heatalways.amazingapplication.core.data.db.di.useDatabaseBeans
import ru.heatalways.amazingapplication.core.data.http_client.di.useHttpClientBeans
import ru.heatalways.amazingapplication.core.data.temp_files.di.useTempFilesStorageBeans
import ru.heatalways.amazingapplication.core.data.translator.di.useTranslatorBeans
import ru.heatalways.amazingapplication.core.data.utils.di.useDataUtilsBeans
import ru.heatalways.amazingapplication.core.navigation.compose_impl.di.useComposeNavigationBeans
import ru.heatalways.amazingapplication.feature.cats.compose_impl.di.includeCatsScope
import ru.heatalways.amazingapplication.feature.facts.compose_impl.di.includeFactsScope
import ru.heatalways.amazingapplication.feature.hate_top.compose_impl.di.includeHateTopScope
import ru.heatalways.amazingapplication.feature.insults.compose_impl.di.includeInsultsScope
import ru.heatalways.amazingapplication.feature.mirror.compose_impl.di.includeMirrorScope
import ru.heatalways.amazingapplication.feature.sharing.android_impl.di.useAndroidSharingBeans
import scout.definition.Registry
import scout.scope

val appScope = scope("app_scope") {
    useApplicationBeans()
    useDispatchersBeans()
    useCoroutineScopesBeans()
    useDatabaseBeans()
    useHttpClientBeans()
    useTempFilesStorageBeans()
    useTranslatorBeans()
    useDataUtilsBeans()
    useComposeNavigationBeans()
    useAndroidSharingBeans()
}.apply {
    includeCatsScope()
    includeFactsScope()
    includeHateTopScope()
    includeInsultsScope()
    includeMirrorScope()
}

private fun Registry.useApplicationBeans() {
    singleton<Context> { AmazingApplication.instance }
}