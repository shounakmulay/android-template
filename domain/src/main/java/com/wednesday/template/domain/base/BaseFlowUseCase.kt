package com.wednesday.template.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.Exception

interface BaseFlowUseCase<IN, OUT> {

    operator fun invoke(param: IN): Flow<Result<OUT>> {
        return invokeInternal(param)
            .map<OUT, Result<OUT>> {
                return@map Result.Success(it)
            }
            .catch { e ->
                emit(Result.Error(e as Exception))
            }
    }

    fun invokeInternal(param: IN): Flow<OUT>
}
