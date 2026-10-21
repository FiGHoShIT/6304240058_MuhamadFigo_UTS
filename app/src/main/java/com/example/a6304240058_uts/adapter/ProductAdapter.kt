
package com.example.a6304240058_uts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a6304240058_uts.R
import com.example.a6304240058_uts.model.Product
import java.util.Locale

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val imgThumbnail: ImageView =
            view.findViewById(R.id.imgThumbnail)

        val tvTitle: TextView =
            view.findViewById(R.id.tvTitle)

        val tvPrice: TextView =
            view.findViewById(R.id.tvPrice)

        val tvStock: TextView =
            view.findViewById(R.id.tvStock)

        val tvRating: TextView =
            view.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = products[position]

        holder.tvTitle.text = product.title

        holder.tvPrice.text = String.format(
            Locale.US,
            "$%.2f",
            product.price
        )

        holder.tvStock.text = "Stok: ${product.stock}"

        holder.tvRating.text = String.format(
            Locale.US,
            "Rating: %.1f",
            product.rating
        )

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.imgThumbnail)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
