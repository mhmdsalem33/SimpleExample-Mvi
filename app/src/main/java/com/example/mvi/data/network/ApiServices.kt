package com.example.mvi.data.network

import com.example.mvi.data.Model.Posts
import retrofit2.Response
import retrofit2.http.GET


interface ApiServices {

    @GET("posts")
    suspend fun getPosts() : Response<List<Posts>>

}