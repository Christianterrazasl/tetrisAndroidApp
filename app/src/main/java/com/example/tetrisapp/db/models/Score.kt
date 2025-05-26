package com.example.tetrisapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Score (

    val name: String = "",
    val score: Long = 0
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}