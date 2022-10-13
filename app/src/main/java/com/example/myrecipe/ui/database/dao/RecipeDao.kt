package com.example.myrecipe.ui.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipe.ui.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun showList(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipe WHERE titulo LIKE :titulo")
    fun searchMenu (titulo: String): Flow<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE id = :id")
    fun searchId(id: Long): Flow<Recipe?>
}