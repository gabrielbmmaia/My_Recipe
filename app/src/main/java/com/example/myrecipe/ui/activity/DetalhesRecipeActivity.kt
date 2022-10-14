package com.example.myrecipe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityDetalhesRecipeBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.example.myrecipe.ui.model.Recipe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesRecipeActivity : AppCompatActivity(R.layout.activity_detalhes_recipe) {

    private var recipeId: Long = 0L
    private val binding by lazy {
        ActivityDetalhesRecipeBinding.inflate(layoutInflater)
    }
    private val recipeDao by lazy {
        AppDatabase.instance(this).recipeDao()
    }
    private val toolbar by lazy {
        binding.detalhesToolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        recebeRecipe()
        tentaCarregarRecipe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_recipe_remover -> {
                MaterialAlertDialogBuilder(this@DetalhesRecipeActivity)
                    .setTitle("Apagar Receita?")
                    .setPositiveButton("Apagar"){_,_ ->
                        lifecycleScope.launch {
                            recipeDao.searchId(recipeId).collect { recipe ->
                                recipe?.let { recipeDao.deleteRecipe(it) }
                                finish()
                            }
                        }
                    }.setNegativeButton("Cancelar"){ _,_ ->
                    }.show()
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
            recipeDao.searchId(recipeId).collect { recipe ->
                recipe?.let { receitaCarregada ->
                    preencherCampos(receitaCarregada)
                    withContext(Dispatchers.Main) {
                        toolbar.title = receitaCarregada.titulo
                    }
//                    toolbar.title = it.titulo
                } ?: finish()
            }
        }
    }

    private fun preencherCampos(recipe: Recipe) {
        with(binding) {
            detalhesActivityImagem.tryLoadImage(recipe.imagemUrl)
            detalhesActivityIngredientesText.text = recipe.ingrediente
            detalhesActivityPreparoText.text = recipe.preparo
        }
    }
}