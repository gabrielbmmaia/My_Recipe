package com.example.myrecipe.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityMainBinding
import com.example.myrecipe.ui.database.AppDatabase
import com.example.myrecipe.ui.recyclerview.adapter.MainActivityAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        configuraRecyclerView()
        atualizaTela()
    }

    private fun atualizaTela() {
        lifecycleScope.launch {
            recipeDao.showList().collect {
                adapter.refresh(it)
            }
        }
    }

    fun configuraRecyclerView() {
        val recyclerView = binding.activityMainRecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = this.adapter
    }
}