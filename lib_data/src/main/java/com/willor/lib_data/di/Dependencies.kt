package com.willor.lib_data.di

import com.willor.lib_data.data.AcromineService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.usecases.SearchByAcronymLongForm
import com.willor.lib_data.domain.usecases.SearchByAcronymShortForm
import com.willor.lib_data.domain.usecases.UseCases
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
        return AcromineService.acromineServiceInstance
    }

    @Provides
    @Singleton
    fun provideRepo(acromineApi: AcromineService): Repo {
        return RepoImpl(acromineApi)
    }

    @Provides
    fun provideUseCaseSearchByAcronymShortForm(repo: Repo): SearchByAcronymShortForm{
        return SearchByAcronymShortForm(repo)
    }
    
    @Provides
    fun provideUseCaseSearchByAcronymLongForm(repo: Repo): SearchByAcronymLongForm{
        return SearchByAcronymLongForm(repo)
    }

    @Provides
    fun provideUseCases(
        searchByAcronymShortForm: SearchByAcronymShortForm,
        searchByAcronymLongForm: SearchByAcronymLongForm
    ): UseCases{
        return UseCases(searchByAcronymShortForm, searchByAcronymLongForm)
    }
}