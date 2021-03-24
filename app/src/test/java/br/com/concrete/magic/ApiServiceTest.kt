package br.com.concrete.magic

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.concrete.magic.data.repository.ApiSuccessResponse
import br.com.concrete.magic.data.source.remote.ApiService
import br.com.concrete.magic.util.getOrAwaitValue
import br.com.concrete.magic.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.System.out

@RunWith(JUnit4::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    suspend fun getRepos() {
        enqueueResponse("repos-yigit.json")
        val repos = (service.getExp().getOrAwaitValue() as ApiSuccessResponse).body
//        val repos = (service.getRepos().getOrAwaitValue() as ApiErrorResponse)

//        out.print("------------- " + repos.errorMessage)

        val request = mockWebServer.takeRequest()
        out.print("------------- " + request.path)
        assertThat(request.path, `is`("sets?type=expansion"))

/*        assertThat(repos.sets.size, `is`(2))

        val repo = repos.sets[0]
        assertThat(repo.fullName, `is`("yigit/AckMate"))

        val owner = repo.owner
        assertThat(owner, notNullValue())
        assertThat(owner.login, `is`("yigit"))
        assertThat(owner.url, `is`("https://api.github.com/users/yigit"))

        val repo2 = repos[1]
        assertThat(repo2.fullName, `is`("yigit/android-architecture"))*/
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
                .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }

}