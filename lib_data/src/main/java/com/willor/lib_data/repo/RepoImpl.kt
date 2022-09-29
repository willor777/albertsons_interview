package com.willor.lib_data.repo

import com.willor.lib_data.data.AcromineService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjects.AcromineResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RepoImpl(
    private val acromineApi: AcromineService
): Repo {

    val tag = RepoImpl::class.java.simpleName

    override suspend fun searchByAcronymShortForm(acronymShortForm: String): Flow<AcromineResp?> = flow {
        val resp = acromineApi.getAcronymSearch(acronymShortForm.uppercase(), "")
        if (resp.isSuccessful && !resp.body().isNullOrEmpty()){
            emit(resp.body())
        }else{
            emit(null)
        }
    }

    override suspend fun searchByAcronymLongForm(acronymLongForm: String): Flow<AcromineResp?> = flow{
        val resp = acromineApi.getAcronymSearch("", acronymLongForm)
        if (resp.isSuccessful && !resp.body().isNullOrEmpty()){
            emit(resp.body())
        }else{
            emit(null)
        }
    }


}