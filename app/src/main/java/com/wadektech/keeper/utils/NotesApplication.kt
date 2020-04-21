package com.wadektech.keeper.utils

import android.app.Application
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NotesApplication : Application() , KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NotesApplication))
        bind() from singleton {
            NoteRoomDatabase(instance()) }

        bind() from singleton {
            NotesRepository(instance()) }

        bind() from provider {
            NotesViewModelFactory(instance()) }
    }

}