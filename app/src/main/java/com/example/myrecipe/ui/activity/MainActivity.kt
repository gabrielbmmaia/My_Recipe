package com.example.myrecipe.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityMainBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.recyclerview.adapter.MainActivityAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        val search = menu?.findItem(R.id.menu_main_acitvity_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
        lifecycleScope.launch {
            recipeDao.searchMenu(searchQuery).collect {
                adapter.refresh(it)
            }
        }
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