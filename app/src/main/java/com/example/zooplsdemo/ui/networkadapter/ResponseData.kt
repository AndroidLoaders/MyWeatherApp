package com.example.zooplsdemo.ui.networkadapter

sealed class ResponseData<Response> {
    class Success<Response>(val responseData: Response?, val responseCode: Int) :
        ResponseData<Response>()

    class Error<Response>(val errorMessage: String, val errorCode: Int) : ResponseData<Response>()
}