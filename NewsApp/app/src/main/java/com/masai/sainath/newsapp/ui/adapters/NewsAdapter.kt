package com.masai.sainath.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.masai.sainath.newsapp.remote.OnItemClicked
import com.masai.sainath.newsapp.remote.responses.ArticleModel
import com.bumptech.glide.Glide
import com.masai.sainath.newsapp.R
import com.masai.sainath.newsapp.databinding.ItemLayoutBinding

class NewsAdapter(
    private val articleModelList: List<ArticleModel>,
    private val OnItemClicked: OnItemClicked
) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemLayoutBinding: ItemLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout, parent, false
            )
        return NewsViewHolder(itemLayoutBinding, OnItemClicked)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articleModel = articleModelList[position]
        holder.onBind(articleModel)
    }

    override fun getItemCount(): Int {
        return articleModelList.size
    }
}

class NewsViewHolder(
    private val itemLayoutBinding: ItemLayoutBinding,
    private val OnItemClicked: OnItemClicked
) :
    RecyclerView.ViewHolder(itemLayoutBinding.root) {

    fun onBind(articleModel: ArticleModel) {
        itemLayoutBinding.onCardClicked = OnItemClicked
        itemLayoutBinding.article = articleModel
        Glide.with(itemLayoutBinding.iCard).load(articleModel.urlToImage)
            .into(itemLayoutBinding.iCard)
    }

}
