package com.dayorolands.run.network.di

import com.dayorolands.core.domain.run.RemoteRunDataSource
import com.dayorolands.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}