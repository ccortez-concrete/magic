package br.com.concrete.magic.domain.usecase

import br.com.concrete.magic.domain.model.Expansion
import br.com.concrete.magic.domain.model.ExpansionView
import br.com.concrete.magic.domain.model.ExpansionViewType
import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.repository.ExpansionsRepository
import com.android.post.domain.usecase.base.UseCase

class GetExpansionsUseCase constructor(
        private val expansionsRepository: ExpansionsRepository
) : UseCase<List<ExpansionView>, Any?>() {

    override suspend fun run(params: Any?): List<ExpansionView> {
        return formatExpansionList(sortExpansions(expansionsRepository.getExpansions()))
//        prepareExpansionViews(
//                sortExpansions(expansionsRepository.getExpansions())
//        )
    }

    fun sortExpansions(root: Root): List<Expansion> {

        val copy = arrayListOf<Expansion>().apply { addAll(root.sets) }
        copy.sort()
        root.sets = copy

        return root.sets
    }

    fun formatExpansionList(expansionList: List<Expansion>): List<ExpansionView> {
        val expansionViewList: MutableList<ExpansionView> = mutableListOf()
        var currentLetter = ""
        expansionList.forEach {
            val firstLetter = it.name.first().toUpperCase().toString()

            if (firstLetter != currentLetter) {
                expansionViewList.add(
                        ExpansionView(ExpansionViewType.SECTION, firstLetter)
                )
                currentLetter = firstLetter
            }
            expansionViewList.add(
                    ExpansionView(ExpansionViewType.ITEM, expansion = it)
            )
        }
        return expansionViewList
    }

}