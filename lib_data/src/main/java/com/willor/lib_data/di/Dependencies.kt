package com.willor.lib_data.di

import com.willor.lib_data.data.AcromineService
import com.willor.lib_data.data.RetrofitInstance
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.repo.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Dependencies {

    @Provides
    @Singleton
    fun provideAcromineService(): AcromineService {
        return RetrofitInstance.acromineServiceInstance
    }

    @Provides
    @Singleton
    fun provideRepo(acromineApi: AcromineService): Repo {
        return RepoImpl(acromineApi)
    }

}