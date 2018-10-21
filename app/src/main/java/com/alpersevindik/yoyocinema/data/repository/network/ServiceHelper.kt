package com.alpersevindik.yoyocinema.data.repository.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceHelper {

    companion object {

        @Volatile
        private var lokmaServiceClient: ServiceClient? = null

        fun getInstance(): ServiceClient =
                lokmaServiceClient
                        ?: synchronized(this) {
                            lokmaServiceClient
                                    ?: buildServiceClient().also { lokmaServiceClient = it }
                        }

        private fun buildServiceClient(): ServiceClient =
                Retrofit.Builder()
                        .baseUrl(ServiceURL.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ServiceClient::class.java)

    }
}

fun <T> Call<T>.enqueue(): NetworkResponse<T> {
    var responseData: NetworkResponse<T>? = null
    responseData = NetworkResponse(retry = { call: Call<T> ->
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                responseData?.error?.value = t
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                if (response?.isSuccessful == true)
                    responseData?.data?.value = response.body()
                else
                    responseData?.error?.value = Throwable(response?.errorBody()?.string())
            }
        })
    })
    responseData.retry(this)
    return responseData
}