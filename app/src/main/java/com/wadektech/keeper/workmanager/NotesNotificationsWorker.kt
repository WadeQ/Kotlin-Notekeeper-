package com.wadektech.keeper.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import retrofit2.HttpException
import timber.log.Timber

class NotesNotificationsWorker(context: Context, params : WorkerParameters) : CoroutineWorker(context,params){
    companion object {
        const val WORK_NAME = "com.wadektech.keeper.workmanager.NotesNotificationWorker"
    }
    override suspend fun doWork(): Result {
        val noteRoomDatabase = NoteRoomDatabase(applicationContext)
        val notesRepository = NotesRepository(noteRoomDatabase)

        try {
            Timber.d("createNotifications(): notification class called.")
            notesRepository.createNotifications()
        }catch (e : HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}