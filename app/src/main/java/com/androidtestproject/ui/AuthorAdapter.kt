package com.androidtestproject.ui

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtestproject.model.AuthorListItem

class AuthorAdapter(
    private val lifecycle: Lifecycle,
    private val eventHAndler: AuthorViewHolder.EventHAndler
) : PagingDataAdapter<AuthorListItem, RecyclerView.ViewHolder>(
    POST_COMPARATOR
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val authoritem = getItem(position)
        if(authoritem!=null){
            (holder as AuthorViewHolder).bind(authoritem,position)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AuthorViewHolder.create(parent,lifecycle,eventHAndler)
    }
    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<AuthorListItem>() {
            override fun areItemsTheSame(
                oldItem: AuthorListItem,
                newItem: AuthorListItem
            ): Boolean =
                oldItem.AuthorId == newItem.AuthorId


            override fun areContentsTheSame(
                oldItem: AuthorListItem,
                newItem: AuthorListItem
            ): Boolean =
                oldItem == newItem
        }
    }
}