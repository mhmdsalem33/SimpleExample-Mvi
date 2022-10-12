package com.example.mvi.data.Repository

import com.example.mvi.data.network.ApiServices
import javax.inject.Inject


class MainRepository @Inject constructor(private val api :  ApiServices){

    suspend fun getPots() = api.getPosts()
}