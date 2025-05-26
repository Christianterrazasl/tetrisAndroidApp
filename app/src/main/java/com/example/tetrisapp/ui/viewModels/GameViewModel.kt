package com.example.tetrisapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val _score = MutableLiveData<Long>(0)
    val score: LiveData<Long> = _score
    private val _level = MutableLiveData<Int>(1)
    val level: LiveData<Int> = _level

    fun setLevel(level: Int){
        _level.value = level
    }
    fun setScore(score: Long){
        _score.value = score
    }
}