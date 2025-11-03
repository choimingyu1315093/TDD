package com.example.tdd.viewmodel.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tdd.model.area.AreaApiService
import com.example.tdd.model.area.AreaMapper
import com.example.tdd.model.area.AreaRepositoryImpl
import com.example.tdd.model.common.ErrorHandler
import com.example.tdd.utils.NetworkUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AreaViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val mapper = AreaMapper()
        val errorHandler = ErrorHandler()
        val api = Retrofit.Builder()
            .baseUrl(NetworkUtil.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AreaApiService::class.java)

        val repo = AreaRepositoryImpl(api, mapper, errorHandler)

        return AreaViewModel(repo, errorHandler) as T
    }
}