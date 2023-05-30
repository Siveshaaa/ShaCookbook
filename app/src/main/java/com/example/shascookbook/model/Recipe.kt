package com.example.shascookbook.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipes_table")
data class Recipe(

    @PrimaryKey(autoGenerate = true)
    val ID: Int,
    val name: String,
    val category: String,
    val prepTime: String,
    val cookTime: String,
    val servingSize: String,
    val ingredients: String,
    val method: String,
    val image: Bitmap,
    val link: String
): Parcelable