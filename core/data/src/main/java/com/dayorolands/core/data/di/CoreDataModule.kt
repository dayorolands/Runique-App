package com.dayorolands.core.data.di

import com.dayorolands.core.data.auth.EncryptedSessionStorage
import com.dayorolands.core.data.networking.HttpClientFactory
import com.dayorolands.core.data.run.OfflineFirstRunRepository
import com.dayorolands.core.domain.SessionStorage
import com.dayorolands.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}