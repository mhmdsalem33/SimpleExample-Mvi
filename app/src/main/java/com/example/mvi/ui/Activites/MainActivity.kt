package com.example.mvi.ui.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.databinding.ActivityMainBinding
import com.example.mvi.ui.adapters.MainAdapter
import com.example.mvi.ui.intent.MainIntent
import com.example.mvi.ui.viewState.MainViewState
import com.example.mvi.ui.viewmodels.MainViewModel

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    private val mainMvvm : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainAdapter = MainAdapter()

        sendIntents()
        setupRecView()
        observeViewModel()

    }


    private fun sendIntents() {
      lifecycleScope.launchWhenStarted {
          mainMvvm.mainIntent.send(MainIntent.GetPosts)
      }
    }

    private fun observeViewModel() {
       lifecycleScope.launchWhenStarted {
           mainMvvm.state.collect{
               when(it)
               {
                   is MainViewState.Loading  -> binding.progressCircular.visibility = View.VISIBLE
                   is MainViewState.Success  -> {
                       mainAdapter.differ.submitList(it.data)
                       binding.progressCircular.visibility = View.GONE
                       binding.mainRec.visibility          = View.VISIBLE
                   }
                   is  MainViewState.Error -> {
                       binding.progressCircular.visibility = View.GONE
                       Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                   }
                   else -> Unit
               }
           }
       }
    }

    private fun setupRecView() {
        binding.mainRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter       = mainAdapter
        }
    }




}