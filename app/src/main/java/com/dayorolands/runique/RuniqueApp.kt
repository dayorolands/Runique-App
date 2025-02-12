package com.dayorolands.runique

import android.app.Application
import android.content.Context
import com.dayorolands.auth.data.di.authDataModule
import com.dayorolands.auth.presentation.di.authViewModelModule
import com.dayorolands.core.data.di.coreDataModule
import com.dayorolands.core.database.di.databaseModule
import com.dayorolands.run.data.di.runDataModule
import com.dayorolands.run.location.di.locationModule
import com.dayorolands.run.network.di.networkModule
import com.dayorolands.run.presentation.di.runPresentationModule
import com.dayorolands.runique.di.appModule
import com.google.android.play.core.splitcompat.SplitCompat
import io.kotzilla.sdk.KotzillaSDK
import io.kotzilla.sdk.analytics.koin.analyticsLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber


class RuniqueApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        KotzillaSDK.setup(this@RuniqueApp)
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            analyticsLogger()
            androidContext(this@RuniqueApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}