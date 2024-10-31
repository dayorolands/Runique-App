package com.dayorolands.run.data.di

import com.dayorolands.core.domain.run.SyncRunScheduler
import com.dayorolands.run.data.CreateRunWorker
import com.dayorolands.run.data.DeleteRunWorker
import com.dayorolands.run.data.FetchRunsWorker
import com.dayorolands.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::DeleteRunWorker)
    workerOf(::FetchRunsWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}