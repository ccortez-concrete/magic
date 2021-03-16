package br.com.concrete.magic.domain.usecase.base

import br.com.concrete.magic.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

