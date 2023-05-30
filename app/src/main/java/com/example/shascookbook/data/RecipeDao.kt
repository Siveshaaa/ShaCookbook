package com.example.shascookbook.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shascookbook.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes_table")
    fun readAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes_table WHERE category LIKE :recipeCategory")
    fun readFilteredRecipes(recipeCategory: String?): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

}