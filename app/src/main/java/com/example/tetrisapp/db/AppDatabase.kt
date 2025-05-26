package com.example.tetrisapp.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tetrisapp.db.dao.ScoreDao
import com.example.tetrisapp.db.models.Score

@Database(
    entities = [Score::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }

}