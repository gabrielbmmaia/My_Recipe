package com.example.myrecipe.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.databinding.RecipeItemBinding
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.example.myrecipe.ui.model.Recipe

class MainActivityAdapter(
    recipe: List<Recipe> = emptyList()
) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private val recipeList = recipe.toMutableList()

    inner class ViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            val recipeTitulo = binding.recyclerviewListRecipeTitulo
            val recipeImagem = binding.recyclerviewListRecipeImagem
            recipeTitulo.text = recipe.titulo
            recipeImagem.tryLoadImage(recipe.imagemUrl)

            val visibility = if (recipe.imagemUrl != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            recipeImagem.visibility
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        RecipeItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).also {
                return ViewHolder(it)
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size
}