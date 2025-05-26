package com.example.tetrisapp.db.repositories

import android.content.Context
import com.example.tetrisapp.db.AppDatabase
import com.example.tetrisapp.db.models.Score

object ScoreRepository {
    suspend fun getAllScores(context: Context) : List<Score>{
        return AppDatabase.getInstance(context).scoreDao().getAllScores()
    }

    suspend fun insertScore(context: Context, score: Score) : Long{
        return AppDatabase.getInstance(context).scoreDao().insertScore(score)
    }

    suspend fun deleteScores(context: Context): Int{
        return AppDatabase.getInstance(context).scoreDao().deleteScores()
    }
}