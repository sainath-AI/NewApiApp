package com.masai.sainath.newsapp.remote.localDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masai.sainath.newsapp.remote.responses.ArticleModel


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResponseFromDB(articleList: List<ArticleModel>)

    @Query("select * from _news_Db")
    fun getDataFromDB(): LiveData<List<ArticleModel>>

    @Query("delete from _news_Db")
    fun deleteAllDataFromDb()

}