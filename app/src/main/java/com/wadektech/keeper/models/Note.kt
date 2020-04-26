package com.wadektech.keeper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings

@Entity(tableName = "notes_db")
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
data class Note(
    @ColumnInfo(name = "note_title")
    val title : String,

    @ColumnInfo(name = "note_body")
    val note : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}