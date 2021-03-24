package br.com.concrete.magic

import br.com.concrete.magic.data.repository.ExpansionsRepositoryImp
import br.com.concrete.magic.data.source.remote.ApiService
import br.com.concrete.magic.domain.model.Root
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ExpansionsRepositoryTest {

    private lateinit var expansionsRepository: ExpansionsRepositoryImp

    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        expansionsRepository = ExpansionsRepositoryImp(apiService)
    }

    @Test
    fun givenGetExpansionsApiCallSuccessfully_thenVerifyExpectedResponse() = runBlocking {
        val expectedResponse: Root = mockk()
        coEvery { apiService.getExpansions() } returns expectedResponse

        val response = expansionsRepository.getExpansions()

        assertEquals(expectedResponse, response)
    }

    @Test(expected = Exception::class)
    fun givenGetExpansionsApiCallWithError_thenVerifyIfThrowException() {

        val error = Exception()
        coEvery { apiService.getExpansions() } throws error

        runBlocking {
            expansionsRepository.getExpansions()
        }

    }

}