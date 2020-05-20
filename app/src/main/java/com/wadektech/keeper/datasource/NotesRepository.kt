package com.wadektech.keeper.datasource

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.wadektech.keeper.R
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.ui.MainActivity
import com.wadektech.keeper.workmanager.application.NotesApplication

class NotesRepository(private val roomDatabase: NoteRoomDatabase) {

 companion object{
     const val  CHANNEL_ID = "channel_id"
     const val CHANNEL_NAME = "channel_name"
 }
    val context = NotesApplication.notesApplicationContext()

    fun getAllNotesFromRoom(): DataSource.Factory<Int, Note> {
        return roomDatabase.noteDao().getAllNotes()
    }

    suspend fun insertNotes(note: Note) {
        roomDatabase.noteDao().saveNote(note)
    }

    suspend fun deleteNotes(note: Note) {
        roomDatabase.noteDao().deleteNotes(note)
    }

    suspend fun updateNote(note: Note) {
        roomDatabase.noteDao().updateNote(note)
    }

    fun createNotifications(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                    .apply {
                        lightColor = Color.GREEN
                        enableLights(true)
                    }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }
}