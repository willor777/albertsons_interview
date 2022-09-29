package com.willor.albertsons_interview.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.domain.dataobjects.AcromineResp
import com.willor.lib_data.domain.dataobjects.LongFormWithVariations
import com.willor.lib_data.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: UseCases
) : ViewModel() {

    val tag: String = MainActivityViewModel::class.java.simpleName

    // Variables to hold the search response + current displayed 'long name variations'
    var searchResults: AcromineResp? = null
    var longNamesWithVariations: List<LongFormWithVariations>? = null

    // States to determine which RecyclerView to display
    private val _showAcronymResultsRv = MutableStateFlow(false)
    val showAcronymResultsRv get() = _showAcronymResultsRv.asStateFlow()

    private val _showLongNamesRv = MutableStateFlow(false)
    val showLongNamesRv get() = _showLongNamesRv.asStateFlow()


    /**
     * Display Secondary Recycler View with Long Name Variations for selected Acronym.
     *
     * Searchs the searchResults to find which item was clicked. Set it as longNamesWithVariations.
     * Then change state to show Recycler view.
     */
    fun showSecondaryRvWithLongNameResults(shortFormAcro: String) {
        if (searchResults.isNullOrEmpty()){return}

        for (item in searchResults!!) {
            if (item.shortForm == shortFormAcro) {
                longNamesWithVariations = item.longFormWithVariations
                break
            }
        }

        // Change state values to show the Long Name recycler view
        _showAcronymResultsRv.value = false
        _showLongNamesRv.value = true
    }

    /**
     * Change the state values to show Primary Search Results.
     */
    fun showPrimaryRvWithSearchResults() {
        _showLongNamesRv.value = false
        _showAcronymResultsRv.value = true
        longNamesWithVariations = null
    }

    /**
     * Hides both recycler views, and removes current search results + long name variations
     */
    fun hideBothRvAndClearSearch() {
        _showAcronymResultsRv.value = false
        _showLongNamesRv.value = false
        longNamesWithVariations = null
        searchResults = null
    }

    /**
     * Calls the repo to search for Acronym
     */
    fun searchByAcronym(shortFormAcro: String, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            hideBothRvAndClearSearch()
            repo.searchByAcronymShortForm(shortFormAcro).catch {

            }.collect {
                if (!it.isNullOrEmpty()) {
                    searchResults = it
                    showPrimaryRvWithSearchResults()
                } else {
                    onFailure()
                }
            }
        }
    }

    /**
     * Calls the repo to search for Acronym by it's Long Name
     */
    fun searchByLongName(longName: String, onFailure: () -> Unit) {
        hideBothRvAndClearSearch()
        viewModelScope.launch(Dispatchers.IO) {
            repo.searchByAcronymLongForm(longName).collectLatest {
                if (!it.isNullOrEmpty()) {
                    searchResults = it
                    showPrimaryRvWithSearchResults()
                } else {
                    onFailure()
                }
            }
        }
    }
}