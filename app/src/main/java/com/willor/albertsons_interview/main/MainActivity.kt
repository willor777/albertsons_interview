package com.willor.albertsons_interview.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.willor.albertsons_interview.databinding.ActivityMainBinding
import com.willor.albertsons_interview.main.adapters.LongFormAdapter
import com.willor.albertsons_interview.main.adapters.SearchResultsRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val tag: String = MainActivity::class.java.simpleName

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
    private fun initViews() {
        with(binding) {

            // Search by acronym button
            btnSearchbyacronym.setOnClickListener {
                viewModel.searchByAcronym(
                    binding.edittxtSearchbyacronym.text.toString(),
                    onFailure = {
                        // Launch a Toast on failure
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(baseContext, "Search Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                hideKeyboard(binding.edittxtSearchbyacronym)
            }

            // Search for acronym button
            btnSearchforacronym.setOnClickListener {
                viewModel.searchByLongName(
                    binding.edittxtSearchforacronym.text.toString(),
                    onFailure = {

                        // Launch a Toast on failure
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(baseContext, "Search Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                hideKeyboard(binding.edittxtSearchforacronym)

            }

            // Back Arrow
            btnBack.setOnClickListener {
                backButtonBehaviour()
            }
        }
    }

    /**
     * Starts the Observers for the ViewModel's states. The states are just to determine which
     * RecyclerView to show.
     */
    private fun initObservers() {

        // Show Primary RecyclerView -- Acronym Search Results
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.showAcronymResultsRv.collectLatest {
                    if (it) {
                        showPrimaryResultsRv()
                    }
                }
            }
        }

        // Show Secondary Recycler view -- Acronym Long Names
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.showLongNamesRv.collectLatest {
                    if (it) {
                        showSecondaryResultsRv()
                    }
                }
            }
        }
    }

    /**
     * Utility function to set Recycler View Adapter to the SearchResultsRvAdapter. Also
     * sets up the onclick listener for the Rv Item
     */
    private fun showPrimaryResultsRv() {
        binding.rvSearchresults.adapter = SearchResultsRvAdapter(viewModel.searchResults!!,
            onItemClicked = {
                viewModel.showSecondaryRvWithLongNameResults(it)
            })
    }

    private fun hidePrimaryResultsRv(){
        binding.rvSearchresults.adapter = null
        viewModel.hideBothRvAndClearSearch()
    }

    /**
     * Utility function to set the Recycler View Adapter to the LongFormAdapter.
     */
    private fun showSecondaryResultsRv() {
        binding.rvSearchresults.adapter = LongFormAdapter(viewModel.longNamesWithVariations!!)
    }

    private fun hideSecondaryShowPrimary(){
        viewModel.showPrimaryRvWithSearchResults()
    }

    private fun hideKeyboard(v: View){
        (
                baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ).hideSoftInputFromWindow(v.windowToken, 0)

        val inputManager = baseContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun backButtonBehaviour(){
        // If secondary Rv is Showing -> show primary
        if (viewModel.showLongNamesRv.value) {
            hideSecondaryShowPrimary()
        }

        // If primary Rv is Showing -> hide primary
        else if (viewModel.showAcronymResultsRv.value) {
            hidePrimaryResultsRv()
        }

        // If neither is showing but search text is present -> Clear text
        else if (
            !viewModel.showAcronymResultsRv.value
            && !viewModel.showAcronymResultsRv.value
            && (binding.edittxtSearchbyacronym.text.isNotBlank()
                    || binding.edittxtSearchforacronym.text.isNotBlank())
        ){
            binding.edittxtSearchbyacronym.setText("")
            binding.edittxtSearchforacronym.setText("")
        }
        // If neither is showing && no search text -> Exit app
        else {
            finishAffinity()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        backButtonBehaviour()
    }
}


