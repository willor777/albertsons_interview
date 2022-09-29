package com.willor.lib_data.domain

import com.willor.lib_data.domain.dataobjects.AcromineResp
import kotlinx.coroutines.flow.Flow

// TODO Replace the 'ResponseBody' with the actual return dataobj from GSON
interface Repo {

    suspend fun searchByAcronymShortForm(acronymShortForm: String): Flow<AcromineResp?>

    suspend fun searchByAcronymLongForm(acronymLongForm: String): Flow<AcromineResp?>

}