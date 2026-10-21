package com.example.a6304240058_uts.network

import com.example.a6304240058_uts.model.Product
import com.example.a6304240058_uts.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int,
        @Query("select") select: String
    ): Call<ProductResponse>

    @GET("products/{id}")
    fun getProductById(
        @Path("id") id: Int
    ): Call<Product>
}