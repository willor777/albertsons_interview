package com.willor.albertsons_interview.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.willor.albertsons_interview.databinding.ActivityMainBinding
import com.willor.albertsons_interview.main.adapters.LongFormAdapter
import com.willor.albertsons_interview.main.adapters.SearchResultsRvAdapter
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

        // Set click Listners
        initViews()

        // Start the observer jobs
        initObservers()

        setContentView(binding.root)
    }

    /**
     * Sets up the onClickListeners for both Search Buttons and Back Button
     */
    fun initViews(){
        with (binding) {

            // Search by acronym button
            btnSearchbyacronym.setOnClickListener {
                viewModel.searchByAcronym(
                    binding.edittxtSearchbyacronym.text.toString(),
                    onFailure = {
                        lifecycleScope.launch(Dispatchers.Main){
                            Toast.makeText(baseContext, "Search Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            // Search for acronym button
            btnSearchforacronym.setOnClickListener {
                viewModel.searchByLongName(
                    binding.edittxtSearchforacronym.text.toString(),
                    onFailure = {
                        lifecycleScope.launch(Dispatchers.Main){
                            Toast.makeText(baseContext, "Search Failed", Toast.LENGTH_SHORT).show()
                        }                    }
                )
            }

            // Back button
            btnBack.setOnClickListener {
                if (viewModel.showLongNamesRv.value){
                    viewModel.showPrimaryRvWithSearchResults()
                }
                else if (viewModel.showAcronymResultsRv.value){
                    viewModel.hideBothRvAndClearSearch()
                }
            }
        }
    }

    /**
     * Starts the Observers for the ViewModel's states. The states are just to determine which
     * RecyclerView to show.
     */
    fun initObservers(){

        // Show Primary RecyclerView -- Acronym Search Results
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.showAcronymResultsRv.collectLatest {
                if (it){
                    showPrimaryResultsRv()
                }
            }

        }

        // Show Secondary Recycler view -- Acronym Long Names
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.showLongNamesRv.collectLatest {
                if (it){
                    showSecondaryResultsRv()
                }
            }
        }
    }

    /**
     * Utility function to set Recycler View Adapter to the SearchResultsRvAdapter. Also
     * sets up the onclick listener for the Rv Item
     */
    private fun showPrimaryResultsRv(){
        binding.rvSearchresults.adapter = SearchResultsRvAdapter(viewModel.searchResults!!,
            onItemClicked = {
                viewModel.showSecondaryRvWithLongNameResults(it)
            })
    }

    /**
     * Utility function to set the Recycler View Adapter to the LongFormAdapter.
     */
    private fun showSecondaryResultsRv(){
        binding.rvSearchresults.adapter = LongFormAdapter(viewModel.longNamesWithVariations!!)
    }





}