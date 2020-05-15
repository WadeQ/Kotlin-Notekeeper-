package com.wadektech.keeper.datasource

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import com.wadektech.keeper.R
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.workmanager.application.NotesApplication

class NotesRepository(private val roomDatabase: NoteRoomDatabase) {

    val context = NotesApplication.notesApplicationContext()

    fun getAllNotesFromRoom(): LiveData<List<Note>> {
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

    fun createNotifications(title : String, description : String){
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, "101")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_notifications)

        notificationManager.notify(1, notificationBuilder.build())

    }

}