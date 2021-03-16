package br.com.concrete.magic.domain.usecase

import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.repository.ExpansionsRepository
import com.android.post.domain.usecase.base.UseCase

class GetExpansionsUseCase constructor(
    private val expansionsRepository: ExpansionsRepository
) : UseCase<Root, Any?>() {

    override suspend fun run(params: Any?): Root {
        return expansionsRepository.getExpansions()
    }

}