package com.example.shascookbook.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shascookbook.R
import com.example.shascookbook.databinding.FragmentFullRecipeBinding
import com.example.shascookbook.viewmodel.RecipeViewModel

class FullRecipeFragment : Fragment() {

    private var _binding: FragmentFullRecipeBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FullRecipeFragmentArgs>()
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFullRecipeBinding.inflate(inflater, container, false)

        // Initialize ViewModel
        mRecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        //Create Menu Host
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_recipe, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_editRecipe -> {
                        val action =
                            FullRecipeFragmentDirections.actionFullRecipeFragmentToUpdateRecipeFragment(
                                args.selectedRecipe, args.cardType
                            )
                        findNavController().navigate(action)
                        return true
                    }

                    R.id.menu_shareRecipe -> {
                        shareRecipe()
                        return true
                    }

                    R.id.menu_deleteRecipe -> {
                        deleteRecipe()
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        binding.TVRecipeName.text = args.selectedRecipe.name
        binding.TVCategoryName.text = args.selectedRecipe.category
        binding.TVPrepTime.text = args.selectedRecipe.prepTime
        binding.TVCookTime.text = args.selectedRecipe.cookTime
        binding.TVServingSize.text = args.selectedRecipe.servingSize
        binding.TVIngredients.text = args.selectedRecipe.ingredients
        binding.TVMethod.text = args.selectedRecipe.method
        binding.IVRecipePic.setImageBitmap(args.selectedRecipe.image)
        val link = args.selectedRecipe.link
        binding.ButWatchVideo.setOnClickListener {
            if (link.isEmpty()) {
                Toast.makeText(requireContext(), "No video link present!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                openVideoLink(link)
            }
        }

        return binding.root
    }

    private fun openVideoLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mRecipeViewModel.deleteRecipe(args.selectedRecipe)
            Toast.makeText(
                requireContext(),
                "Successfully deleted ${args.selectedRecipe.name}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Recipe?")
        builder.setMessage("Are you sure you want to delete ${args.selectedRecipe.name}?")
        builder.create().show()
    }

    @Suppress("DEPRECATION")
    private fun shareRecipe() {
        val link: String = args.selectedRecipe.link.ifEmpty {
            "Unavailable"
        }
        val lineBreaker = "\n"
        val doubleLineBreaker = "\n\n"
        val recipeText: String = "Recipe: ${args.selectedRecipe.name}${lineBreaker}" +
                "Category: ${args.selectedRecipe.category}${lineBreaker}" +
                "Prep Time: ${args.selectedRecipe.prepTime}${lineBreaker}" +
                "Cook Time: ${args.selectedRecipe.cookTime}${lineBreaker}" +
                "Serving Size: ${args.selectedRecipe.servingSize}${doubleLineBreaker}" +
                "Ingredients:${lineBreaker}${args.selectedRecipe.ingredients}${doubleLineBreaker}" +
                "Method:${lineBreaker}${args.selectedRecipe.method}${doubleLineBreaker}" +
                "Link: $link"
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType(mimeType)
            .setChooserTitle("Share recipe with:")
            .setText(recipeText)
            .startChooser()
    }

}

