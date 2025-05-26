package com.example.tetrisapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrisapp.R
import com.example.tetrisapp.databinding.ActivityGameOverBinding
import com.example.tetrisapp.db.models.Score
import com.example.tetrisapp.ui.adapters.ScoreAdapter
import com.example.tetrisapp.ui.viewModels.ScoreViewModel

class GameOverActivity : AppCompatActivity() {
    private val viewModel: ScoreViewModel by viewModels()
    private lateinit var binding: ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
        setupRecyclerView()
        viewModel.loadScores(this)
        viewModel.scoreList.observe(this){
                scores ->
            (binding.scoreRv.adapter as ScoreAdapter).apply {
                this.scores.clear()
                this.scores.addAll(scores)
                notifyDataSetChanged()}
        }
    }

    private fun setupRecyclerView() {
        val adapter = ScoreAdapter(arrayListOf())
        binding.scoreRv.layoutManager = LinearLayoutManager(this)
        binding.scoreRv.adapter = adapter

    }

    private fun setupEventListeners(){
        binding.btnRestartMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSend.setOnClickListener {
            val scoreLong = intent.getLongExtra("score", 0)
            val scoreName = binding.inputName.text.toString()
            if(scoreName.isNotBlank() && scoreName.isNotEmpty()){
                viewModel.addScore(this, Score(scoreName, scoreLong))
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnDeleteScores.setOnClickListener {
            viewModel.deleteScores(this)
        }

    }
}