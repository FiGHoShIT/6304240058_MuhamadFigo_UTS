package com.example.a6304240058_uts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a6304240058_uts.adapter.ProductAdapter
import com.example.a6304240058_uts.model.Product
import com.example.a6304240058_uts.model.ProductResponse
import com.example.a6304240058_uts.network.ApiConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView

    private val selectedFields =
        "id,title,description,category,price,stock," +
                "availabilityStatus,tags,dimensions,reviews," +
                "rating,images,thumbnail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvProducts = findViewById(R.id.rvProducts)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)

        rvProducts.layoutManager = LinearLayoutManager(this)

        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.selectedItemId = R.id.nav_products

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_products -> true

                R.id.nav_profile -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java)
                    )
                    false
                }

                else -> false
            }
        }

        getProducts()
    }

    private fun getProducts() {
        progressBar.visibility = View.VISIBLE
        tvError.visibility = View.GONE

        ApiConfig.apiService.getProducts(
            limit = 25,
            select = selectedFields
        ).enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null && result.products.isNotEmpty()) {
                        rvProducts.adapter = ProductAdapter(
                            result.products
                        ) { product ->
                            val intent = Intent(
                                this@MainActivity,
                                DetailActivity::class.java
                            )

                            intent.putExtra("product_id", product.id)
                            startActivity(intent)
                        }
                    } else {
                        showError("Data produk kosong.")
                    }
                } else {
                    showError(
                        "Gagal mengambil data. HTTP ${response.code()}"
                    )
                }
            }

            fun putExtra(string: String, id: Any) {}

            override fun onFailure(
                call: Call<ProductResponse>,
                t: Throwable
            ) {
                progressBar.visibility = View.GONE
                showError(
                    "Koneksi gagal: ${t.message ?: "Periksa internet"}"
                )
            }
        })
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}