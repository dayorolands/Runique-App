package com.dayorolands.run.presentation.di

import com.dayorolands.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runViewmodelDi = module {
    viewModelOf(::RunOverviewViewModel)
}