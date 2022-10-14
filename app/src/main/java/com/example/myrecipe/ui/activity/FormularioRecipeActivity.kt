package com.example.myrecipe.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myrecipe.R
import com.example.myrecipe.databinding.AcitivityFormularioRecipeBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.dialog.FormularioDialog
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.example.myrecipe.ui.model.Recipe
import kotlinx.coroutines.launch

class FormularioRecipeActivity : AppCompatActivity(R.layout.acitivity_formulario_recipe) {

    private var recipeId = 0L
    private var url: String? = null
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
        pegarRecipe()
        tentaCarregarRecipe()
        alterarImagem()
    }

    fun alterarImagem() {
        binding.formularioAcitvityRecipeImagem.setOnClickListener {
            FormularioDialog(this).alterarImagem(url) { imagem ->
                this.url = imagem
                binding.formularioAcitvityRecipeImagem.tryLoadImage(url)
            }
        }
    }

    private fun tentaCarregarRecipe() {
        lifecycleScope.launch {
            recipeDao.searchId(recipeId).collect { recipe ->
                recipe?.let {
                    preencherCampos(it)
                }
            }
        }
    }

    private fun configurarBotaoSalvar() {
        binding.formularioRecipeBotaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                recipeDao.addRecipe(novaReceita())
                val intent = Intent(this@FormularioRecipeActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun pegarRecipe() {
        recipeId = intent.getLongExtra(CHAVE_RECIPE, 0L)
    }

    private fun preencherCampos(recipe: Recipe) {
        this.url = recipe.imagemUrl
        binding.formularioRecipeEditTextTitulo.setText(recipe.titulo)
        binding.formularioRecipeEditTextIngredientes.setText(recipe.ingrediente)
        binding.formularioRecipeEditTextPreparo.setText(recipe.preparo)
        binding.formularioAcitvityRecipeImagem.tryLoadImage(recipe.imagemUrl)
    }

    private fun novaReceita(): Recipe {
        val campoTitulo = binding.formularioRecipeEditTextTitulo.text.toString()
        val campoIngredientes = binding.formularioRecipeEditTextIngredientes.text.toString()
        val campoPreparo = binding.formularioRecipeEditTextPreparo.text.toString()
        binding.formularioAcitvityRecipeImagem.tryLoadImage(url)

        return Recipe(
            id = recipeId,
            titulo = campoTitulo,
            ingrediente = campoIngredientes,
            preparo = campoPreparo,
            imagemUrl = url
        )
    }
}

