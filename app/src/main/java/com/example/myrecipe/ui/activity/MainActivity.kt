package com.example.myrecipe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.databinding.ActivityMainBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.recyclerview.adapter.MainActivityAdapter
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private val adapter = MainActivityAdapter()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val recipeDao by lazy {
        AppDatabase.instance(this).recipeDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuracaoRecyclerView()
        atualizaTela()
        configuracaoEfab()
    }

    private fun atualizaTela() {
        lifecycleScope.launch {
            recipeDao.showList().collect {
                adapter.refresh(it)
            }
        }
    }

    private fun configuracaoEfab() {
        binding.activityMainEfab.setOnClickListener {
            Intent(this, FormularioRecipeActivity::class.java)
                .apply {
                    startActivity(this)
                }
        }
    }

    fun configuracaoRecyclerView() {
        val recyclerView = binding.activityMainRecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = this.adapter

        adapter.onClickItem = {
            Intent(this, DetalhesRecipeActivity::class.java)
                .apply {
                    putExtra(CHAVE_RECIPE, it.id)
                    startActivity(this)
                }
        }
        adapter.onLongClickItem = {
            Intent(this, FormularioRecipeActivity::class.java)
                .apply {
                    putExtra(CHAVE_RECIPE, it.id)
                    startActivity(this)
                }
        }
    }
}