package com.masai.sainath.newsapp.remote

import com.masai.sainath.newsapp.remote.responses.ResponseModel
import retrofit2.http.GET

interface APIClient {

    @GET("v2/everything?q=apple&from=2021-12-04&to=2021-12-04&sortBy=popularity&apiKey=cb048cd738504d8e9b073176ba23ddae")
    suspend fun getApiResponse() : ResponseModel

}