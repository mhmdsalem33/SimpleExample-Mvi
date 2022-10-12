package com.example.mvi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.data.Model.Posts
import com.example.mvi.data.Repository.MainRepository
import com.example.mvi.ui.intent.MainIntent
import com.example.mvi.ui.viewState.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo : MainRepository ) : ViewModel() {

    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state :StateFlow<MainViewState> get() = _state

    init {
        processIntent()
    }

    private fun processIntent()
    {
       viewModelScope.launch {
           mainIntent.consumeAsFlow().collect {
             when(it)
             {
                 is MainIntent.GetPosts -> getPosts()
             }
           }
       }
    }


   private fun getPosts()
    {
             _state.value = MainViewState.Loading
            viewModelScope.launch {
                try {
                    val response = mainRepo.getPots()
                        response.body()?.let {
                           _state.value = MainViewState.Success(data = it)
                       }
                }catch (e :Throwable) {
                _state.value = MainViewState.Error(message = e.message.toString())
                 }
            }
    }
}

