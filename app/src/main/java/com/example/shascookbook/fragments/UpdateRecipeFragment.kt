package com.example.shascookbook.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shascookbook.R
import com.example.shascookbook.databinding.FragmentUpdateRecipeBinding
import com.example.shascookbook.model.Recipe
import com.example.shascookbook.viewmodel.RecipeViewModel
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION")
class UpdateRecipeFragment : Fragment() {

    private var _binding: FragmentUpdateRecipeBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_CODE_GALLERY = 999
    private lateinit var imageHolder: ImageView
    private lateinit var mRecipeViewModel: RecipeViewModel
    private val args by navArgs<UpdateRecipeFragmentArgs>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateRecipeBinding.inflate(inflater, container, false)

        // Initialize ViewModel
        mRecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        //Set data retrieved to the layout
        binding.ETRecipeName.setText(args.selectedRecipe.name)
        binding.SpinnerRecipeCategory.setText(args.selectedRecipe.category)
        binding.ETPrepTime.setText(args.selectedRecipe.prepTime)
        binding.ETCookTime.setText(args.selectedRecipe.cookTime)
        binding.ETServingSize.setText(args.selectedRecipe.servingSize)
        binding.ETIngredients.setText(args.selectedRecipe.ingredients)
        binding.ETMethod.setText(args.selectedRecipe.method)
        binding.IVUploadRecipePic.setImageBitmap(args.selectedRecipe.image)
        binding.ETVideoLink.setText(args.selectedRecipe.link)

        binding.ImageCardView.setOnClickListener {
            imageHolder = binding.IVUploadRecipePic
            uploadImage()
        }

        binding.ButUpdateRecipe.setOnClickListener {
            onUpdate()
        }

        binding.ButCancelRecipe.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val recipeCategory = resources.getStringArray(R.array.RecipeTypes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, recipeCategory)
        binding.SpinnerRecipeCategory.setAdapter(arrayAdapter)
    }

    private fun uploadImage() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Intent(MediaStore.ACTION_PICK_IMAGES)
        } else {
            Intent(Intent.ACTION_PICK)
        }
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val uri = data.data
            imageHolder.setImageURI(uri)
        }
    }

    private fun onUpdate() {

        if (dataValidation()) {
            Toast.makeText(requireContext(), "Fields Required!", Toast.LENGTH_SHORT).show()
        } else {
            updateRecipe()
        }
    }

    private fun updateRecipe() {
        val recipeName = binding.ETRecipeName.text.toString()
        val recipeCategory = binding.SpinnerRecipeCategory.text.toString()
        val prepTime = binding.ETPrepTime.text.toString()
        val cookTime = binding.ETCookTime.text.toString()
        val servingSize = binding.ETServingSize.text.toString()
        val ingredients = binding.ETIngredients.text.toString()
        val method = binding.ETMethod.text.toString()
        val recipeImage = binding.IVUploadRecipePic.drawable.toBitmap()
        val link = binding.ETVideoLink.text.toString()

        val updatedRecipe = Recipe(
            args.selectedRecipe.ID,
            recipeName,
            recipeCategory,
            prepTime,
            cookTime,
            servingSize,
            ingredients,
            method,
            recipeImage,
            link
        )

        mRecipeViewModel.updateRecipe(updatedRecipe)
        Toast.makeText(requireContext(), "Recipe Updated!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun dataValidation(): Boolean {

        setError(binding.ETRecipeName.text.toString().isEmpty(), binding.RecipeName)
        setError(binding.SpinnerRecipeCategory.text.toString().isEmpty(), binding.Spinner)
        setError(binding.ETPrepTime.text.toString().isEmpty(), binding.PrepTime)
        setError(binding.ETCookTime.text.toString().isEmpty(), binding.CookTime)
        setError(binding.ETServingSize.text.toString().isEmpty(), binding.ServingSize)
        setError(binding.ETIngredients.text.toString().isEmpty(), binding.Ingredients)
        setError(binding.ETMethod.text.toString().isEmpty(), binding.Method)

        return dataValidationChecker()
    }

    private fun dataValidationChecker(): Boolean {
        return (binding.ETRecipeName.text.toString().isEmpty()
                || binding.SpinnerRecipeCategory.text.toString().isEmpty()
                || binding.ETPrepTime.text.toString().isEmpty()
                || binding.ETCookTime.text.toString().isEmpty()
                || binding.ETServingSize.text.toString().isEmpty()
                || binding.ETIngredients.text.toString().isEmpty()
                || binding.ETMethod.text.toString().isEmpty()
                )
    }

    private fun setError(error: Boolean, ET: TextInputLayout) = if (error) {
        ET.isErrorEnabled = true
        ET.error = "Required field!"
    } else {
        ET.isErrorEnabled = false
    }

}