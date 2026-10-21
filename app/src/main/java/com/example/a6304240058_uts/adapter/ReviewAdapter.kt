package com.example.a6304240058_uts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a6304240058_uts.R
import com.example.a6304240058_uts.model.Review
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ReviewAdapter(
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val initial: TextView =
            view.findViewById(R.id.tvReviewerInitial)

        val name: TextView =
            view.findViewById(R.id.tvReviewerName)

        val date: TextView =
            view.findViewById(R.id.tvReviewDate)

        val rating: TextView =
            view.findViewById(R.id.tvReviewRating)

        val comment: TextView =
            view.findViewById(R.id.tvReviewComment)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int
    ) {
        val review = reviews[position]

        holder.initial.text =
            review.reviewerName.trim().firstOrNull()
                ?.uppercaseChar()?.toString() ?: "?"

        holder.name.text = review.reviewerName
        holder.comment.text = review.comment

        val stars = "★".repeat(review.rating.coerceIn(0, 5)) +
                "☆".repeat(5 - review.rating.coerceIn(0, 5))

        holder.rating.text =
            "$stars  ${review.rating}/5"

        holder.date.text = formatDate(review.date)
    }

    private fun formatDate(value: String): String {
        return try {
            val input = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.US
            ).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val output = SimpleDateFormat(
                "dd MMMM yyyy",
                Locale("id", "ID")
            )

            output.format(input.parse(value)!!)
        } catch (e: Exception) {
            value
        }
    }

    override fun getItemCount(): Int = reviews.size
}
