package com.willor.albertsons_interview.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.willor.albertsons_interview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        initViews()
        viewModel.testing()     // TODO Delete
        setContentView(binding.root)
    }

    private fun initViews(){
        // TODO Repeat on lifecycle
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.searchResultsStateFlow.collectLatest {
                if (!it.isNullOrEmpty()){
                    binding.rvSearchresults.adapter = SearchResultsRvAdapter(it)
                }
            }
        }
    }

}