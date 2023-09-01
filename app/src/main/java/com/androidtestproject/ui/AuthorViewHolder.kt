package com.androidtestproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.androidtestproject.R
import com.androidtestproject.model.AuthorListItem
import com.androidtestproject.util.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class AuthorViewHolder(
    private val view: View, private val lifecycle: Lifecycle, private val eventHAndler: EventHAndler
) : RecyclerView.ViewHolder(view) {
    private val authorname: TextView = view.findViewById(R.id.idAuthorName)
    private val authorImage: ImageView = view.findViewById(R.id.idAuthorImage)
    private val lnparent:LinearLayout = view.findViewById(R.id.ln_parent)

    private lateinit var authorListItem: AuthorListItem
    private var viewHolderPosition = 0

    init {
        attachListner()
    }

    fun bind(authorListItem: AuthorListItem, position: Int, inflateAuthorContent: Boolean = true) {
        viewHolderPosition = position
        this.authorListItem = authorListItem
        showAuthor(authorListItem, inflateAuthorContent)

    }

    private fun showAuthor(authorListItem: AuthorListItem, inflateAuthorContent: Boolean) {
        authorname.text = authorListItem.author
        Glide.with(view)
            .load(authorListItem.download_url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(authorImage)

    }

    private fun attachListner(){
        lnparent.setOnClickListener { view->
            eventHAndler.onItemClicked(view,viewHolderPosition,authorListItem)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            lifecycle: Lifecycle,
            eventHAndler: EventHAndler
        ): AuthorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.author_item, parent, false)
            return AuthorViewHolder(view, lifecycle, eventHAndler)
        }
    }

    interface EventHAndler {
        fun onItemClicked(view: View, position: Int, authorListItem: AuthorListItem)
    }
}