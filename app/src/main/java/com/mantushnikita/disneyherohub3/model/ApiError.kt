package com.mantushnikita.disneyherohub3.model

sealed class ApiError {
    data object Error404:ApiError()
    data object Error403:ApiError()
    data object Error401:ApiError()
}