package com.willor.albertsons_interview.mainactivityviewmodel_test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test


class RepoTest {

    val repo = FakeRepo()

    @Test
    fun `searchByAcronym blank argument returns null`(){

        runBlocking {
            val out = repo.searchByAcronymShortForm("").first()
            assert(out == null)
        }
    }

    @Test
    fun `searchByAcronym valid argument returns value`(){
        runBlocking {
            val out = repo.searchByAcronymShortForm("HMM").first()
            assert(out != null)
        }
    }

    @Test
    fun `searchByLongFormAcronym null return from empty arg`(){
        runBlocking {
            val out = repo.searchByAcronymLongForm("").first()
            assert(out == null)
        }
    }

    @Test
    fun `searchByLongFormAcronym non-null return from valid arg`(){
        runBlocking {
            val out = repo.searchByAcronymLongForm("Hello Mad Max").first()
            assert(out != null)
        }
    }

}