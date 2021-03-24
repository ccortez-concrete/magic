package br.com.concrete.magic.data.source.remote

import androidx.lifecycle.LiveData
import br.com.concrete.magic.data.repository.ApiResponse
import br.com.concrete.magic.domain.model.Root
import retrofit2.http.GET

interface ApiService {

    @GET("sets?type=expansion")
    fun getExp(): LiveData<ApiResponse<Root>>

    @GET("sets?type=expansion")
    suspend fun getExpansions(): Root

}