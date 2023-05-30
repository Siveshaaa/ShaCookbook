package com.example.shascookbook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shascookbook.R
import com.example.shascookbook.fragments.HomepageFragment
import com.example.shascookbook.fragments.HomepageFragmentDirections
import com.example.shascookbook.model.Recipe

class TodaysSuggestionAdapter(
    val context: HomepageFragment, private val cardType: String
) : RecyclerView.Adapter<TodaysSuggestionAdapter.TodaysSuggestionViewHolder>() {

    private var adapterTodaysSuggestionList = emptyList<Recipe>()

    class TodaysSuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView = view.findViewById(R.id.IV_RecipePic)
        val recipeName: TextView = view.findViewById(R.id.TV_RecipeName)
        val todaysSuggestionCard: LinearLayout = view.findViewById(R.id.todaysSuggestionCard)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaysSuggestionViewHolder {

        return TodaysSuggestionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todays_suggestions_cards, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return adapterTodaysSuggestionList.size

    }

    override fun onBindViewHolder(holder: TodaysSuggestionViewHolder, position: Int) {
        val todaysSuggestionItem = adapterTodaysSuggestionList[position]
        holder.recipeImage.setImageBitmap(todaysSuggestionItem.image)
        holder.recipeName.text = todaysSuggestionItem.name

        holder.todaysSuggestionCard.setOnClickListener {
            val action = HomepageFragmentDirections.actionHomepageFragmentToFullRecipeFragment(
                todaysSuggestionItem, cardType
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToTodaysSuggestionCards(todaysSuggestionList: List<Recipe>) {
        adapterTodaysSuggestionList = todaysSuggestionList
        notifyDataSetChanged()
    }
}
