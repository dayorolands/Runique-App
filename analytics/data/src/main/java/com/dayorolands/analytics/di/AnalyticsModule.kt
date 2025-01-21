package com.dayorolands.analytics.di

import com.dayorolands.analytics.data.RoomAnalyticsRepository
import com.dayorolands.analytics.domain.AnalyticsRepository
import com.dayorolands.core.database.RunDatabase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}