package com.willor.albertsons_interview.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjects.AcromineResp
import com.willor.lib_data.domain.dataobjects.LongFormWithVariations
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
    private val repo: Repo
): ViewModel() {

    val tag = MainActivityViewModel::class.java.simpleName

    var searchResults: AcromineResp? = null
    var longNamesWithVariations: List<LongFormWithVariations>? = null

    private val _showAcronymResultsRv = MutableStateFlow(false)
    val showAcronymResultsRv get() = _showAcronymResultsRv.asStateFlow()

    private val _showLongNamesRv = MutableStateFlow(false)
    val showLongNamesRv get() = _showLongNamesRv.asStateFlow()


    fun showSecondaryRvWithLongNameResults(shortFormAcro: String){
        for (item in searchResults!!){
            if (item.shortForm == shortFormAcro){
                longNamesWithVariations = item.longFormWithVariations
            }
        }
        _showAcronymResultsRv.value = false
        _showLongNamesRv.value = true
    }

    fun showPrimaryRvWithSearchResults(){
        _showLongNamesRv.value = false
        _showAcronymResultsRv.value = true
        longNamesWithVariations = null
    }

    fun hideBothRvAndClearSearch(){
        _showAcronymResultsRv.value = false
        _showLongNamesRv.value = false
        longNamesWithVariations = null
        searchResults = null
    }

    fun searchByAcronym(shortFormAcro: String, onFailure: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            hideBothRvAndClearSearch()
            repo.searchByAcronymShortForm(shortFormAcro).catch{

            }.collect {
                if (!it.isNullOrEmpty()){
                    searchResults = it
                    showPrimaryRvWithSearchResults()
                }
                else{
                    onFailure()
                }
            }
        }
    }

    fun searchByLongName(longName: String, onFailure: () -> Unit){
        hideBothRvAndClearSearch()
        viewModelScope.launch(Dispatchers.IO){
            repo.searchByAcronymLongForm(longName).collectLatest {
                if (!it.isNullOrEmpty()){
                    searchResults = it
                    showPrimaryRvWithSearchResults()
                }
                else{
                    onFailure()
                }
            }
        }
    }
}