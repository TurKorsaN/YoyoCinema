package com.alpersevindik.yoyocinema.data.repository.network

import androidx.lifecycle.MutableLiveData
import retrofit2.Call

data class NetworkResponse<T>(
        val data: MutableLiveData<T> = MutableLiveData(),
        val error: MutableLiveData<Throwable> = MutableLiveData(),
        val retry: (Call<T>) -> Unit
)