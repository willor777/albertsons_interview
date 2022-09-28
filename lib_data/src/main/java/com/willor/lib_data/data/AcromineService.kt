package com.willor.lib_data.data

import com.willor.lib_data.domain.dataobjects.AcromineResp
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface AcromineService {

    @GET("dictionary.py")
    suspend fun getAcronymSearch(
        @Query(PARAM_SHORT_FORM_OF_ACRO) shortForm: String,
        @Query(PARAM_LONG_FORM_OF_ACRO) longForm: String
    ): Response<AcromineResp?>


    companion object{
        const val ENDPOINT = "http://www.nactem.ac.uk/software/acromine/"
        const val PARAM_SHORT_FORM_OF_ACRO = "sf"
        const val PARAM_LONG_FORM_OF_ACRO = "lf"

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AcromineService.ENDPOINT)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .build()
            )
            .build()

        val acromineServiceInstance = retrofit.create(AcromineService::class.java)
    }
}