package com.example.tetrisapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrisapp.databinding.ScoreItemBinding
import com.example.tetrisapp.db.models.Score

class ScoreAdapter(var scores: ArrayList<Score>): RecyclerView.Adapter<ScoreAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScoreItemBinding.inflate(
            inflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = scores[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    class ViewHolder(private val binding: ScoreItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Score){
            binding.lblName.text = item.name
            binding.lblScoreLong.text = item.score.toString()
        }
    }


}