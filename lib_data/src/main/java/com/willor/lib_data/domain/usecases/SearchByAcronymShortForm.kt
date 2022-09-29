package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjects.AcromineResp
import kotlinx.coroutines.flow.Flow

class SearchByAcronymShortForm(
    private val repo: Repo
) {

    suspend operator fun invoke(acronymShortForm: String): Flow<AcromineResp?> =
        repo.searchByAcronymShortForm(acronymShortForm)
}