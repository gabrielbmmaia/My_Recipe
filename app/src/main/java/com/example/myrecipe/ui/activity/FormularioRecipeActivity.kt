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

    private val recipeId = 0L
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
        configurarBotaoSalvar()
        title = "Nova Receita"
    }

    private fun configurarBotaoSalvar() {
        binding.formularioRecipeBotaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                recipeDao.addRecipe(novaReceita())
                finish()
            }
        }
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

