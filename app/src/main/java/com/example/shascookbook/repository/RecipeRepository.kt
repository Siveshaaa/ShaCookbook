package com.example.shascookbook.repository

import androidx.lifecycle.LiveData
import com.example.shascookbook.data.RecipeDao
import com.example.shascookbook.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllRecipes: LiveData<List<Recipe>> = recipeDao.readAllRecipes()

    fun readFilteredRecipes(cardType: String): List<Recipe> {
        return recipeDao.readFilteredRecipes(cardType)
    }

    suspend fun addRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

}