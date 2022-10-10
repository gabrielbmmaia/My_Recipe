package com.example.myrecipe.ui.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipe.ui.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun showList(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)
}