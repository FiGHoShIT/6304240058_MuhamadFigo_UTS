package com.example.a6304240058_uts.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("category")
    val category: String = "",

    @SerializedName("price")
    val price: Double = 0.0,

    @SerializedName("stock")
    val stock: Int = 0,

    @SerializedName("availabilityStatus")
    val availabilityStatus: String = "",

    @SerializedName("tags")
    val tags: List<String> = emptyList(),

    @SerializedName("dimensions")
    val dimensions: Dimensions = Dimensions(),

    @SerializedName("reviews")
    val reviews: List<Review> = emptyList(),

    @SerializedName("rating")
    val rating: Double = 0.0,

    @SerializedName("images")
    val images: List<String> = emptyList(),

    @SerializedName("thumbnail")
    val thumbnail: String = ""
)