package com.example.shascookbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shascookbook.R
import com.example.shascookbook.data.CategoryDataSource
import com.example.shascookbook.fragments.HomepageFragmentDirections
import com.google.android.material.card.MaterialCardView

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val categoryImage: ImageView = view.findViewById(R.id.IV_CategoryPic)
        val categoryName: TextView = view.findViewById(R.id.TV_CategoryName)
        val categoryCard: MaterialCardView = view.findViewById(R.id.categoryCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.category_cards, parent, false)
        return CategoryViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int =
        CategoryDataSource.categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = CategoryDataSource.categoryList[position]
        holder.categoryImage.setImageResource(categoryItem.categoryImage)
        holder.categoryName.text = categoryItem.name
        holder.categoryCard.setOnClickListener {
            val action =
                HomepageFragmentDirections.actionHomepageFragmentToRecipeListFragment(categoryItem.name)
            holder.itemView.findNavController().navigate(action)
        }

    }

}