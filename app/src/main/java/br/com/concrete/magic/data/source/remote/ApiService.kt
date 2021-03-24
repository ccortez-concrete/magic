package br.com.concrete.magic.data.source.remote

import br.com.concrete.magic.domain.model.Root
import retrofit2.http.GET

interface ApiService {

    @GET("sets?type=expansion")
    suspend fun getExpansions(): Root

}