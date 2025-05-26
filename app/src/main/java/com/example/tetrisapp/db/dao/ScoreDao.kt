package com.example.tetrisapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tetrisapp.db.models.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM Score ORDER BY score DESC")
    suspend fun getAllScores(): List<Score>

    @Insert
    suspend fun insertScore(score: Score): Long

    @Query("DELETE FROM Score")
    suspend fun deleteScores(): Int
}