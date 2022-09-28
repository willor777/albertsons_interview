package com.willor.albertsons_interview.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjects.AcromineResp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: Repo
): ViewModel() {

    val tag = MainActivityViewModel::class.java.simpleName

    private val _searchResultsStateFlow = MutableStateFlow<AcromineResp?>(null)
    val searchResultsStateFlow get() = _searchResultsStateFlow.asStateFlow()

    // TODO Delete me
    fun testing(){
        viewModelScope.launch(Dispatchers.IO) {
            val search = repo.searchByAcronymShortForm("AAH")
            Log.d("TESTING", "$search")
            Log.d("TESTING", "${search!!.size}")
            if (!search.isNullOrEmpty()){
                _searchResultsStateFlow.value = search
            }
        }
    }
}