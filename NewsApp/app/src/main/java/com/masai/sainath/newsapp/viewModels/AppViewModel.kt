package com.masai.sainath.newsapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.masai.sainath.newsapp.remote.Resource
import com.masai.sainath.newsapp.remote.responses.ArticleModel
import com.masai.sainath.newsapp.remote.responses.ResponseModel
import com.masai.sainath.newsapp.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(var appRepository: AppRepository) : ViewModel() {


    fun getDataFromAPI(): LiveData<Resource<ResponseModel>> {
        return liveData(Dispatchers.IO) {
            val data = appRepository.getDataFromApi()
            emit(data)
        }
    }

    fun insertDataToDb(articleModel: List<ArticleModel>) {
        appRepository.insertDatatInDb(articleModel)
    }

    fun getDataFromDb(): LiveData<List<ArticleModel>> {
        return appRepository.getDataFromDb()
    }

    fun deleteDataFromDb() {
        appRepository.deleteFromDb()
    }

}