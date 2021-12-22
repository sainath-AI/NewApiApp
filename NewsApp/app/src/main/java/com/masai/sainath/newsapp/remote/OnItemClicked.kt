package com.masai.sainath.newsapp.remote

import com.masai.sainath.newsapp.remote.responses.ArticleModel

interface OnItemClicked {

    fun onCardClicked(articleModel: ArticleModel)

}