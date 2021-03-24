package br.com.concrete.magic.presentation.expansions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.concrete.magic.domain.model.ApiError
import br.com.concrete.magic.domain.model.Expansion
import br.com.concrete.magic.domain.model.ExpansionView
import br.com.concrete.magic.domain.model.Root
import br.com.concrete.magic.domain.usecase.GetExpansionsUseCase
import br.com.concrete.magic.domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel


class ExpansionsViewModel constructor(private val getExpansionsUseCase: GetExpansionsUseCase) : ViewModel() {

    val expansionsData = MutableLiveData<List<ExpansionView>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    fun getExpansions() {
        showProgressbar.value = true
        getExpansionsUseCase.invoke(viewModelScope, null, object :
            UseCaseResponse<List<ExpansionView>> {
                override fun onSuccess(result: List<ExpansionView>) {
                    Log.i(TAG, "result: $result")
                    expansionsData.value = result
                    showProgressbar.value = false
                }

                override fun onError(apiError: ApiError?) {
                    messageData.value = apiError?.getErrorMessage()
                    showProgressbar.value = false
                }
            },
        )
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = ExpansionsViewModel::class.java.name
    }

}