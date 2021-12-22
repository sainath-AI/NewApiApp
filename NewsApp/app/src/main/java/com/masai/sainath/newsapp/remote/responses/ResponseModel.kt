package com.masai.sainath.newsapp.remote.responses


import com.google.gson.annotations.SerializedName
import com.masai.sainath.newsapp.remote.responses.ArticleModel

data class ResponseModel(
    @SerializedName("articles")
    val articleModels: List<ArticleModel>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)