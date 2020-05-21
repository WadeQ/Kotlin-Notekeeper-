package com.wadektech.keeper.workmanager.application

import android.app.Application
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.wadektech.keeper.R
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.ui.MainActivity
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
        val intent = Intent(notesApplicationContext(), MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this@NotesApplication).run {
            addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder = NotificationCompat.Builder(this@NotesApplication,
            NotesRepository.CHANNEL_ID)
            .setContentTitle("New note?")
            .setContentText("Time to add a new note?")
            .setSmallIcon(R.drawable.ic_notifications)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this@NotesApplication)
        notificationManager.notify(1, notificationBuilder)

        val constraints = Constraints.Builder()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)
                }
            }
            .build()
        val repeatedRequests = PeriodicWorkRequestBuilder<NotesNotificationsWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        Timber.d("Periodic notifications request scheduled.")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NotesNotificationsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatedRequests)
    }
}