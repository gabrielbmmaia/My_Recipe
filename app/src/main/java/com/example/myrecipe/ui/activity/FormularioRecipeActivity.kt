package com.example.myrecipe.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myrecipe.R
import com.example.myrecipe.databinding.AcitivityFormularioRecipeBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.model.Recipe
import kotlinx.coroutines.launch


class FormularioRecipeActivity : AppCompatActivity(R.layout.acitivity_formulario_recipe) {

    private var recipeId = 0L
    private val url: String? = null
    private val binding by lazy {
        AcitivityFormularioRecipeBinding.inflate(layoutInflater)
    }
    private val recipeDao by lazy {
        AppDatabase.instance(this).recipeDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Nova Receita"
        configurarBotaoSalvar()
        pegarRecipe()
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        lifecycleScope.launch {
            recipeDao.searchId(recipeId).collect {
                it?.let {
                    preencherCampos(it)
                    title = "Editando ${it.titulo}"
                    binding.formularioRecipeBotaoSalvar.text = "Editar"
                }
            }
        }
    }

    private fun configurarBotaoSalvar() {
        binding.formularioRecipeBotaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                recipeDao.addRecipe(novaReceita())
                finish()
            }
        }
    }

    private fun pegarRecipe() {
        intent.getLongExtra(CHAVE_RECIPE, 0L).also {
            recipeId = it
        }
    }

    private fun preencherCampos(recipe: Recipe) {
        binding.formularioRecipeEditTextTitulo.setText(recipe.titulo)
        binding.formularioRecipeEditTextIngredientes.setText(recipe.ingrediente)
        binding.formularioRecipeEditTextPreparo.setText(recipe.preparo)
    }

    private fun novaReceita(): Recipe {
        val campoTitulo = binding.formularioRecipeEditTextTitulo.text.toString()
        val campoIngredientes = binding.formularioRecipeEditTextIngredientes.text.toString()
        val campoPreparo = binding.formularioRecipeEditTextPreparo.text.toString()

        return Recipe(
            id = recipeId,
            titulo = campoTitulo,
            ingrediente = campoIngredientes,
            preparo = campoPreparo,
            imagemUrl = url
        )
    }
}

