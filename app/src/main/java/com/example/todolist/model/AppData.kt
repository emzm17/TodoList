package com.example.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.DB

@Database(entities = [Todo::class], version = 1)
abstract class AppData :RoomDatabase(){
  abstract fun todoDAO(): TodoDao

  companion object {
    @Volatile
    private var INSTANCE: AppData? = null

    fun getDatabase(context: Context): AppData {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(
                context.applicationContext,
                AppData::class.java,
                DB
        ).build()
        INSTANCE = instance
        return instance
      }
    }
  }


}