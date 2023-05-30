package com.example.shascookbook.model

import androidx.annotation.DrawableRes

data class Category(
    @DrawableRes val categoryImage: Int,
    val name: String
)
