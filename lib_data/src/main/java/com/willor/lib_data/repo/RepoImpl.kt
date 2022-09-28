package com.willor.lib_data.repo

import android.util.Log
import com.willor.lib_data.data.AcromineService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjects.AcromineResp


class RepoImpl(
    private val acromineApi: AcromineService
): Repo {

    val tag = RepoImpl::class.java.simpleName

    override suspend fun searchByAcronymShortForm(acronymShortForm: String): AcromineResp? {
        return try{
            val resp = acromineApi.getAcronymSearch(acronymShortForm.uppercase(), "")
            if (resp.isSuccessful && !resp.body().isNullOrEmpty()){
                return resp.body()
            }else{
                null
            }
        } catch(e: java.lang.Exception){
            Log.d(tag, "searchByAcronymShortForm Triggered an Exception: ${e.toString()}" +
                    "\n${e.stackTraceToString()}")
            null
        }

    }

    override suspend fun searchByAcronymLongForm(acronymLongForm: String): AcromineResp? {
        return try{
            val resp = acromineApi.getAcronymSearch("", acronymLongForm)
            if (resp.isSuccessful && !resp.body().isNullOrEmpty()){
                return resp.body()
            }else{
                null
            }
        } catch(e: java.lang.Exception){
            Log.d(tag, "searchByAcronymShortForm Triggered an Exception: ${e.toString()}" +
                    "\n${e.stackTraceToString()}")
            null
        }
    }


}