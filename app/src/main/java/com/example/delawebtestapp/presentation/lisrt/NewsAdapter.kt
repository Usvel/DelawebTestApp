package com.example.delawebtestapp.presentation.factory

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.delawebtestapp.R
import com.example.delawebtestapp.databinding.CardviewNewsBinding
import com.example.delawebtestapp.domain.entitys.News

class NewsAdapter(
    var items: MutableList<News> = mutableListOf(),
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cardview_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position])
        holder.view.setOnClickListener {
            onClick(position)
        }
    }

    fun setListNews(newItems: MutableList<News>) {
        items = newItems
    }

    override fun getItemCount() = items.size
}

class NewsViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = CardviewNewsBinding.bind(view)

    fun bind(news: News) {
        binding.cardViewTitleNews.text = news.title
        binding.cardViewDescriptionNews.text = news.description
        Glide.with(view).load(news.urlImage).listener(object : RequestListener<Drawable> {
            @SuppressLint("ResourceType")
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                binding.cardViewImageNews.setImageResource(R.raw.image_news)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
            .into(binding.cardViewImageNews)
    }
}

class DiffCallback(oldList: List<News>, newList: List<News>) : DiffUtil.Callback() {

    private val oldList = oldList.toList()
    private val newList = newList.toList()

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos].title == newList[newPos].title
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos] == newList[newPos]
    }
}