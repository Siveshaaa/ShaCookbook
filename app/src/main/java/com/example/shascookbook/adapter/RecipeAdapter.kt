package com.example.shascookbook.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shascookbook.R
import com.example.shascookbook.fragments.RecipeListFragment
import com.example.shascookbook.fragments.RecipeListFragmentDirections
import com.example.shascookbook.model.Recipe
import com.google.android.material.card.MaterialCardView

class RecipeAdapter(val context: RecipeListFragment, private val cardType: String) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var adapterRecipeList = emptyList<Recipe>()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val recipeImage: ImageView = view.findViewById(R.id.IV_RecipePic)
        val recipeName: TextView = view.findViewById(R.id.TV_RecipeName)
        val recipeCategory: TextView = view.findViewById(R.id.TV_CategoryName)
        val recipePrepTime: TextView = view.findViewById(R.id.TV_PrepTime)
        val recipeCookTime: TextView = view.findViewById(R.id.TV_CookTime)
        val recipeServingSize: TextView = view.findViewById(R.id.TV_ServingSize)
        val recipeCard: MaterialCardView = view.findViewById(R.id.recipeCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_cards, parent, false)
        )
    }

    override fun getItemCount(): Int {
        val size = adapterRecipeList.size
        Log.d("recipelist", "getItemCount: $size")
        return size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeItem = adapterRecipeList[position]
        holder.recipeName.text = recipeItem.name
        holder.recipeCategory.text = recipeItem.category
        holder.recipePrepTime.text = recipeItem.prepTime
        holder.recipeCookTime.text = recipeItem.cookTime
        holder.recipeServingSize.text = recipeItem.servingSize
        holder.recipeImage.setImageBitmap(recipeItem.image)

        holder.recipeCard.setOnClickListener {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToFullRecipeFragment(
                recipeItem,
                cardType
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToRecipeCards(recipeList: List<Recipe>) {
        adapterRecipeList = recipeList
        notifyDataSetChanged()
    }

}