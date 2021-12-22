package com.masai.sainath.newsapp.remote.responses


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "_news_Db")
data class ArticleModel(
//    @SerializedName("author")
//    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
//    @SerializedName("source")
//    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
) : Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null
}