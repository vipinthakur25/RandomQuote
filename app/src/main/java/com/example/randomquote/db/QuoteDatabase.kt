package com.example.randomquote.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.randomquote.models.Result

@Database(entities = [Result::class], version = 2)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        QuoteDatabase::class.java, "quoteDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}