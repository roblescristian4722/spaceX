package com.example.spacex.data.network.impl

import retrofit2.Response

open class BaseImpl {
    protected fun <T>handleQuery(query: Response<T>): QResult {
        if (query.isSuccessful) {
            query.body()?.let {
                return QResult.Success(it)
            }
        }
        query.errorBody()?.let {
            return QResult.Failure(it)
        }
        return QResult.Failure("failed getting http response")
    }
}