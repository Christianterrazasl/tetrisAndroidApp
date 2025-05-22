package com.example.tetrisapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tetrisapp.R
import com.example.tetrisapp.databinding.ActivityMainBinding
import com.example.tetrisapp.ui.viewModels.GameViewModel
import androidx.activity.viewModels
import com.example.tetrisapp.ui.components.Board
import com.example.tetrisapp.ui.components.Board.OnLevelUpdateListener
import com.example.tetrisapp.ui.components.Board.OnScoreUpdateListener

class MainActivity : AppCompatActivity(), OnScoreUpdateListener, OnLevelUpdateListener {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.boardComponent.scoreUpdateListener = this
        binding.boardComponent.levelUpdateListener = this

        gameViewModel.score.observe(this) { score ->
            binding.scoreText.text = score.toString()
        }
        gameViewModel.level.observe(this) { level ->
            binding.levelText.text = level.toString()
        }

        setupEventListeners()
        binding.boardComponent.startFalling()
    }

    private fun setupEventListeners() {
        binding.btnRotate.setOnClickListener {
            binding.boardComponent.rotate()
        }
        binding.btnDrop.setOnClickListener {
            binding.boardComponent.drop()
        }
        binding.btnRestart.setOnClickListener {
            binding.boardComponent.restart()
        }
        binding.btnExit.setOnClickListener {
            binding.boardComponent.goToGameOverScreen()
        }
    }

    override fun onScoreUpdated(score: Long) {
        gameViewModel.setScore(score)
    }
    override fun onLevelUpdated(level: Int) {
        gameViewModel.setLevel(level)
    }
}