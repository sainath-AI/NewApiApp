package com.masai.sainath.newsapp.repositories

import androidx.lifecycle.LiveData
import com.masai.sainath.newsapp.di.AppModule
import com.masai.sainath.newsapp.remote.Resource
import com.masai.sainath.newsapp.remote.ResponseHandler
import com.masai.sainath.newsapp.remote.localDb.AppDao
import com.masai.sainath.newsapp.remote.responses.ArticleModel
import com.masai.sainath.newsapp.remote.responses.ResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppRepository @Inject constructor(val appDao: AppDao) {

    var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getDataFromApi(): Resource<ResponseModel> {
        return try {
            val response = AppModule.provideApiService().getApiResponse()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }



    fun insertDatatInDb(articleModel: List<ArticleModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.deleteAllDataFromDb()
            appDao.insertResponseFromDB(articleModel)
        }
    }

    fun getDataFromDb(): LiveData<List<ArticleModel>> {
        return appDao.getDataFromDB()
    }

    fun deleteFromDb(){
        appDao.deleteAllDataFromDb()
    }

}