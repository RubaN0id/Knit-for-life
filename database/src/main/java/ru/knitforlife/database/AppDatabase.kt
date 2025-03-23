package ru.knitforlife.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.BindsInstance
import dagger.Component
import ru.knitforlife.database.dao.ColorDao
import ru.knitforlife.database.di.DbModule
import ru.knitforlife.database.dto.Color

@Database(entities = [Color::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun colorDao(): ColorDao

    companion object {
        fun createInstance( context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, "color.db")
                .build()
        }
    }
}