package br.com.concrete.magic.presentation.expansions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.concrete.magic.domain.model.ApiError
import br.com.concrete.magic.domain.model.Expansion
import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.usecase.GetExpansionsUseCase
import br.com.concrete.magic.domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel


class ExpansionsViewModel constructor(private val getExpansionsUseCase: GetExpansionsUseCase) : ViewModel() {

    val expansionsData = MutableLiveData<Root>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    fun getExpansions() {
        showProgressbar.value = true
        getExpansionsUseCase.invoke(viewModelScope, null, object :
            UseCaseResponse<Root> {
                override fun onSuccess(result: Root) {
                    Log.i(TAG, "result: $result")
                    expansionsData.value = result
                    showProgressbar.value = false
                    getExpansionsByLetter(result)
                }

                override fun onError(apiError: ApiError?) {
                    messageData.value = apiError?.getErrorMessage()
                    showProgressbar.value = false
                }
            },
        )
    }

    fun getExpansionsByLetter(root: Root) {
        val letters = Array(26) {i -> ('a' + i).toString()}

        letters.forEachIndexed { index, element ->
            println("Argument $index is $element")
        }

//        val mapExpansionsByLetter = hashMapOf<String, Expansion>()
        var mapExpansionsByLetter : HashMap<String, Expansion>
                = HashMap<String, Expansion> ()

        root.sets.forEach {
            mapExpansionsByLetter.put(it.name.get(0).toString(), it)
        }

        mapExpansionsByLetter.entries.forEach {
            println("Letter ${it.key} is ${it.value.name}")
        }


    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = ExpansionsViewModel::class.java.name
    }

}