package com.example.myrecipe.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityDetalhesRecipeBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.example.myrecipe.ui.model.Recipe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetalhesRecipeActivity : AppCompatActivity(R.layout.activity_detalhes_recipe) {

    private var recipeId: Long = 0L
    private var recipe: Recipe? = null
    private val binding by lazy {
        ActivityDetalhesRecipeBinding.inflate(layoutInflater)
    }
    private val recipeDao by lazy {
        AppDatabase.instance(this).recipeDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recebeRecipe()
        tentaProcurarRecipe()
    }

    private fun recebeRecipe() {
        recipeId = intent.getLongExtra(CHAVE_RECIPE, 0L)
    }

    private fun tentaProcurarRecipe() {
        lifecycleScope.launch {
            recipeDao.searchId(recipeId).collect {
                it?.let {
                    preencherCampos(it)
                    title = it.titulo
                } ?: finish()
            }
        }
    }

    private fun preencherCampos(recipe: Recipe) {
        with(binding) {
            detalhesActivityImagem.tryLoadImage(recipe.imagemUrl)
            detalhesActivityTitulo.text = recipe.titulo
            detalhesActivityIngredientesText.text = recipe.ingrediente
            detalhesActivityPreparoText.text = recipe.preparo
        }
    }

}
