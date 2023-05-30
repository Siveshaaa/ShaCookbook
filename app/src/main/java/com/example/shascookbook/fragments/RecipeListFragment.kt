package com.example.shascookbook.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shascookbook.adapter.RecipeAdapter
import com.example.shascookbook.databinding.FragmentRecipeListBinding
import com.example.shascookbook.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mRecipeViewModel: RecipeViewModel
    private val args by navArgs<RecipeListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Inflate the layout for this fragment (Display Recipe Cards)
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        //Recipe List RecyclerView
        val adapter = RecipeAdapter(this, args.cardType)
        binding.recipeRecyclerView.layoutManager =
            LinearLayoutManager(context)
        binding.recipeRecyclerView.adapter = adapter
        binding.recipeRecyclerView.setHasFixedSize(true)

        //RecipeViewModel
        mRecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        if (args.cardType == "all") {
            mRecipeViewModel.readAllRecipe.observe(viewLifecycleOwner) { recipe ->
                adapter.setDataToRecipeCards(recipe)
            }
        } else {
            //Display filtered recipe cards
            lifecycleScope.launch {
                val list = mRecipeViewModel.readFilteredRecipes(args.cardType)
                Log.e("list", "${list.size}")
                adapter.setDataToRecipeCards(list)
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
