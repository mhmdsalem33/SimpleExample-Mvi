package com.example.mvi.ui.viewState

import com.example.mvi.data.Model.Posts
import retrofit2.Response

sealed class MainViewState {

    object Idle    : MainViewState()
    object Loading : MainViewState()
    data class Success(val data :List<Posts>) : MainViewState()
    data class Error(val message : String)    : MainViewState()

}