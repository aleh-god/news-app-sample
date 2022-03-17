package by.godevelopment.newsappsample.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.newsappsample.R
import by.godevelopment.newsappsample.data.datamodel.Article
import by.godevelopment.newsappsample.databinding.ItemRvBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var newsList: List<Article>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    inner class ItemViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NewsAdapter.ItemViewHolder, position: Int) {
        holder.binding.apply {
            val article = newsList[position]
            textAuthor.text = article.author
            textContent.text = article.content
            textDescription.text = article.description
            textPublishedAt.text = article.publishedAt
            textSource.text = article.source.name
            textTitle.text = article.title
            Glide.with(root)
                .load(article.urlToImage)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.image_not_loaded)
                .placeholder(R.drawable.image)
                .into(image)
//            root.setOnClickListener {
//                onClick.invoke(src)
//            }
        }
    }

    override fun getItemCount(): Int = newsList.size
}