package br.com.concrete.magic.data.repository

import br.com.concrete.magic.data.source.remote.ApiService
import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.repository.ExpansionsRepository

class ExpansionsRepositoryImp(private val apiService: ApiService) : ExpansionsRepository {

    override suspend fun getExpansions(): Root {
        return apiService.getExpansions()
    }
}