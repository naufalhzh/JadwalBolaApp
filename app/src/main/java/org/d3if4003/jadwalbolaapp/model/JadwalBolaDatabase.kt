package org.d3if4003.jadwalbolaapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class JadwalBolaDatabase: RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDAO
    
    companion object{
        @Volatile
        private var INSTANCE: JadwalBolaDatabase? = null
        
        fun getDatabase(context: Context): JadwalBolaDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JadwalBolaDatabase::class.java,
                    "db_jadwalbola"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}