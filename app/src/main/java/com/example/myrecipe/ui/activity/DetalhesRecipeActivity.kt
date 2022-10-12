package com.example.myrecipe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityDetalhesRecipeBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.example.myrecipe.ui.model.Recipe
import kotlinx.coroutines.launch

class DetalhesRecipeActivity : AppCompatActivity(R.layout.activity_detalhes_recipe) {

    private var recipeId: Long = 0L
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
        tentaCarregarRecipe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detalhes_recipe_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_recipe_remover -> {
                lifecycleScope.launch {
                    recipeDao.searchId(recipeId).collect {
                        it?.let { recipeDao.deleteRecipe(it) }
                        finish()
                    }
                }
            }
            R.id.menu_detalhes_recipe_editar -> {
                Intent(this, FormularioRecipeActivity::class.java).apply {
                    putExtra(CHAVE_RECIPE, recipeId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun recebeRecipe() {
        recipeId = intent.getLongExtra(CHAVE_RECIPE, 0L)
    }

    private fun tentaCarregarRecipe() {
        lifecycleScope.launch {
            recipeDao.searchId(recipeId).collect {
                it?.let {
                    preencherCampos(it)
                    title.apply {
                        if (it.titulo.isBlank()) title = "Sem Título"
                        else title = it.titulo
                    }
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