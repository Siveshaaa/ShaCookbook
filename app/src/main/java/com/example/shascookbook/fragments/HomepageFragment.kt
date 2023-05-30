package com.example.shascookbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shascookbook.R
import com.example.shascookbook.adapter.CategoryAdapter
import com.example.shascookbook.adapter.TodaysSuggestionAdapter
import com.example.shascookbook.databinding.FragmentHomepageBinding
import com.example.shascookbook.viewmodel.RecipeViewModel


class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Category RecyclerView
        binding.categoryRecyclerView.adapter = CategoryAdapter()
        binding.categoryRecyclerView.setHasFixedSize(true)

        // Today's Suggestion RecyclerView
        val todaysSuggestionAdapter = TodaysSuggestionAdapter(this, "all")
        binding.todaysSuggestionRecyclerView.adapter = todaysSuggestionAdapter
        binding.todaysSuggestionRecyclerView.setHasFixedSize(true)
        binding.todaysSuggestionRecyclerView.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(context, 2)
        mRecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        mRecipeViewModel.readAllRecipe.observe(viewLifecycleOwner) { recipe ->
            todaysSuggestionAdapter.setDataToTodaysSuggestionCards(recipe.shuffled().take(8))
        }

        binding.ButViewAll.setOnClickListener {
            val cardType = "all"
            val action =
                HomepageFragmentDirections.actionHomepageFragmentToRecipeListFragment(cardType)
            findNavController().navigate(action)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_addRecipeFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}