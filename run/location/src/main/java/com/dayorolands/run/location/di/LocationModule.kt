package com.dayorolands.run.location.di

import com.dayorolands.run.domain.LocationObserver
import com.dayorolands.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}