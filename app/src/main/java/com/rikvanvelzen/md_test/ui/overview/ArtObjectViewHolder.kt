package com.rikvanvelzen.md_test.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.rikvanvelzen.md_test.R
import com.rikvanvelzen.md_test.model.ArtObject

class ArtObjectViewHolder(
    private val view: View,
    private val circularProgressDrawable: CircularProgressDrawable
) : RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.findViewById(R.id.image)
    private val objectNumber: TextView = view.findViewById(R.id.objectNumber)
    private val title: TextView = view.findViewById(R.id.title)
    private val author: TextView = view.findViewById(R.id.author)

    private var artObject: ArtObject? = null

    init {
        view.setOnClickListener {
            artObject?.objectNumber?.let {
                view.findNavController().navigate(
                    R.id.action_overviewFragment_to_detailFragment,
                    bundleOf(OBJECT_NUMBER to it)
                )
            }
        }
    }

    fun bind(artObject: ArtObject?) {
        if (artObject == null) {
            // Loading indication could go here
            // But for the sake of the time constraints of the coding challenge
            // I have only added a circular progress indicator on the ImageView
        } else {
            showArtObjectCard(artObject)
        }
    }

    private fun showArtObjectCard(artObject: ArtObject) {
        this.artObject = artObject

        author.text = artObject.author
        title.text = artObject.title
        objectNumber.text = artObject.objectNumber

        Glide.with(view.context)
            .load(artObject.imageDetail.url)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(image)

    }

    companion object {
        const val OBJECT_NUMBER = "objectNumber"

        fun create(parent: ViewGroup): ArtObjectViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.art_object_view_item, parent, false)

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            return ArtObjectViewHolder(view, circularProgressDrawable)
        }
    }
}
