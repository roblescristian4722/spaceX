package com.example.spacex.data.network.impl

sealed class QResult {
    data class Success(val content: Any?) : QResult()
    data class Failure(val content: Any?): QResult()
}

fun QResult.onSuccess(callback: (Any?) -> Unit): QResult {
    if (this is QResult.Success) {
        callback(content)
    }
    return this
}

fun QResult.onFailure(callback: (String?) -> Unit): QResult {
    if (this is QResult.Failure) {
        callback(content as? String?)
    }
    return this
}