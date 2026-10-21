package com.example.a6304240058_uts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a6304240058_uts.model.Product
import com.example.a6304240058_uts.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvStock = findViewById<TextView>(R.id.tvStock)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvRating = findViewById<TextView>(R.id.tvRating)
        val tvTags = findViewById<TextView>(R.id.tvTags)
        val tvDimensions = findViewById<TextView>(R.id.tvDimensions)
        val tvReview = findViewById<TextView>(R.id.tvReview)
        val tvAllReviews = findViewById<TextView>(R.id.tvAllReviews)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvError = findViewById<TextView>(R.id.tvError)

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val productId = intent.getIntExtra("product_id", -1)

        if (productId == -1) {
            tvError.text = "ID produk tidak valid."
            tvError.visibility = View.VISIBLE
            return
        }

        tvAllReviews.setOnClickListener {
            val product = currentProduct

            if (product != null) {
                val intent = Intent(
                    this@DetailActivity,
                    ReviewActivity::class.java
                )

                intent.putExtra("product_id", product.id)
                startActivity(intent)
            }
        }

        progressBar.visibility = View.VISIBLE

        ApiConfig.apiService.getProductById(productId)
            .enqueue(object : Callback<Product> {

                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    progressBar.visibility = View.GONE

                    if (!response.isSuccessful) {
                        tvError.text =
                            "Gagal memuat detail: HTTP ${response.code()}"
                        tvError.visibility = View.VISIBLE
                        return
                    }

                    val product = response.body()

                    if (product == null) {
                        tvError.text = "Data produk kosong."
                        tvError.visibility = View.VISIBLE
                        return
                    }

                    currentProduct = product

                    tvTitle.text = product.title
                    tvPrice.text = String.format(
                        Locale.US, "$%.2f", product.price
                    )
                    tvCategory.text = "Kategori: ${product.category}"
                    tvDescription.text =
                        "Deskripsi:\n${product.description}"
                    tvStock.text = "Stok: ${product.stock}"
                    tvStatus.text =
                        "Status: ${product.availabilityStatus}"
                    tvRating.text = "Rating: ${product.rating}/5"
                    tvTags.text =
                        "Tags: ${product.tags.joinToString(", ")}"

                    val d = product.dimensions
                    tvDimensions.text =
                        "Dimensi: lebar ${d.width}, " +
                                "tinggi ${d.height}, kedalaman ${d.depth}"

                    val review = product.reviews.firstOrNull()

                    tvReview.text = if (review != null) {
                        "Review pertama\n" +
                                "Rating: ${review.rating}/5\n" +
                                "${review.comment}\n" +
                                "Oleh: ${review.reviewerName}"
                    } else {
                        "Belum ada review."
                    }

                    tvAllReviews.visibility =
                        if (product.reviews.size > 1) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }

                    val imageUrl =
                        product.images.firstOrNull()
                            ?: product.thumbnail

                    Glide.with(this@DetailActivity)
                        .load(imageUrl)
                        .placeholder(
                            android.R.drawable.ic_menu_gallery
                        )
                        .error(
                            android.R.drawable.ic_menu_report_image
                        )
                        .into(imgProduct)
                }

                override fun onFailure(
                    call: Call<Product>,
                    t: Throwable
                ) {
                    progressBar.visibility = View.GONE
                    tvError.text =
                        "Koneksi gagal: ${t.message ?: "Periksa internet"}"
                    tvError.visibility = View.VISIBLE
                }
            })
    }

    private fun showAllReviews(product: Product) {
        val reviews = product.reviews

        if (reviews.isEmpty()) {
            Toast.makeText(
                this,
                "Belum ada review.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val reviewTexts = reviews.mapIndexed { index, review ->
            "${index + 1}. ${review.reviewerName}\n" +
                    "Rating: ${review.rating}/5\n" +
                    "${review.comment}\n" +
                    "Tanggal: ${review.date}"
        }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Semua Review (${reviews.size})")
            .setItems(reviewTexts, null)
            .setPositiveButton("Tutup", null)
            .show()
    }
}