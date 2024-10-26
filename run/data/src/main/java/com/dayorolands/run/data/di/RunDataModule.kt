package com.dayorolands.run.data.di

import com.dayorolands.run.data.CreateRunWorker
import com.dayorolands.run.data.DeleteRunWorker
import com.dayorolands.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::DeleteRunWorker)
    workerOf(::FetchRunsWorker)
}