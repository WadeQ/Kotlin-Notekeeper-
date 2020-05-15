package com.wadektech.keeper.workmanager.application

import android.app.Application
import android.os.Build
import androidx.work.*
import com.wadektech.keeper.workmanager.NotesNotificationsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NotesApplication : Application() {
    private val scope = CoroutineScope(Default)

    init {
        instance = this
    }

    companion object {
        private var instance: NotesApplication? = null

        fun notesApplicationContext() : NotesApplication {
            return instance as NotesApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        delayedNotificationInit()
    }

    private fun delayedNotificationInit(){
        scope.launch {
            Timber.plant(Timber.DebugTree())
            setUpRecurringNotificationWork()
        }
    }
    private fun setUpRecurringNotificationWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)
                }
            }
            .build()
        val repeatedRequests = PeriodicWorkRequestBuilder<NotesNotificationsWorker>(10, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        Timber.d("Periodic notifications request scheduled.")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NotesNotificationsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatedRequests)
    }
}