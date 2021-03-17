package br.com.concrete.magic.domain.usecase

import br.com.concrete.magic.domain.model.Expansion
import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.repository.ExpansionsRepository
import com.android.post.domain.usecase.base.UseCase

class GetExpansionsUseCase constructor(
    private val expansionsRepository: ExpansionsRepository
) : UseCase<Root, Any?>() {

    override suspend fun run(params: Any?): Root {
        return sortExpansions(expansionsRepository.getExpansions())
    }

    fun sortExpansions(root: Root): Root {

        val copy = arrayListOf<Expansion>().apply { addAll(root.sets) }
        copy.sort()
        root.sets = copy

        return root
    }

}