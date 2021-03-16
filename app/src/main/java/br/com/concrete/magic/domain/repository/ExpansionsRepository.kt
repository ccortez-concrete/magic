package br.com.concrete.magic.domain.repository

import br.com.concrete.magic.domain.model.Root

interface ExpansionsRepository {

    suspend fun getExpansions(): Root
}