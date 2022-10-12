package com.example.mvi.ui.intent

sealed class MainIntent {
    object GetPosts : MainIntent()
}