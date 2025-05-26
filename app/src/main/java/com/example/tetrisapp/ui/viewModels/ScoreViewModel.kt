package com.example.tetrisapp.ui.viewModels

import android.app.Person
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrisapp.db.models.Score
import com.example.tetrisapp.db.repositories.ScoreRepository
import kotlinx.coroutines.launch

class ScoreViewModel: ViewModel() {
    private val _scoreList: MutableLiveData<ArrayList<Score>> = MutableLiveData(arrayListOf())
    val scoreList: MutableLiveData<ArrayList<Score>> = _scoreList

    fun loadScores(context: Context){
        viewModelScope.launch {
            scoreList.postValue(
                ScoreRepository.getAllScores(context) as ArrayList<Score>
            )
        }
    }

    fun addScore(context: Context, score: Score){
        viewModelScope.launch {
            ScoreRepository.insertScore(context, score)
            loadScores(context)
        }
    }
    fun deleteScores(context: Context){
        viewModelScope.launch {
            ScoreRepository.deleteScores(context)
            loadScores(context)
        }
    }
}