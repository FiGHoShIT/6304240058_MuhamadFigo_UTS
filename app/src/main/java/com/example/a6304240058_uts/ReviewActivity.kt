
package com.example.a6304240058_uts

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a6304240058_uts.adapter.ReviewAdapter
import com.example.a6304240058_uts.model.Product
import com.example.a6304240058_uts.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val tvProductName =
            findViewById<TextView>(R.id.tvProductName)

        val tvReviewSummary =
            findViewById<TextView>(R.id.tvReviewSummary)

        val tvEmpty =
            findViewById<TextView>(R.id.tvEmpty)

        val progressBar =
            findViewById<ProgressBar>(R.id.progressBar)

        val rvReviews =
            findViewById<RecyclerView>(R.id.rvReviews)

        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val productId = intent.getIntExtra("product_id", -1)

        if (productId == -1) {
            tvEmpty.text = "ID produk tidak valid."
            tvEmpty.visibility = View.VISIBLE
            return
        }

        rvReviews.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE

        ApiConfig.apiService.getProductById(productId)
            .enqueue(object : Callback<Product> {

                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    progressBar.visibility = View.GONE

                    if (!response.isSuccessful) {
                        tvEmpty.text =
                            "Gagal mengambil review: HTTP ${response.code()}"
                        tvEmpty.visibility = View.VISIBLE
                        return
                    }

                    val product = response.body()

                    if (product == null) {
                        tvEmpty.text = "Data produk tidak ditemukan."
                        tvEmpty.visibility = View.VISIBLE
                        return
                    }

                    tvProductName.text = product.title

                    val reviews = product.reviews

                    tvReviewSummary.text =
                        "${reviews.size} ulasan pelanggan"

                    if (reviews.isEmpty()) {
                        tvEmpty.text = "Belum ada ulasan untuk produk ini."
                        tvEmpty.visibility = View.VISIBLE
                    } else {
                        rvReviews.adapter = ReviewAdapter(reviews)
                    }
                }

                override fun onFailure(
                    call: Call<Product>,
                    t: Throwable
                ) {
                    progressBar.visibility = View.GONE
                    tvEmpty.text =
                        "Koneksi gagal: ${t.message ?: "Periksa internet"}"
                    tvEmpty.visibility = View.VISIBLE
                }
            })
    }
}
