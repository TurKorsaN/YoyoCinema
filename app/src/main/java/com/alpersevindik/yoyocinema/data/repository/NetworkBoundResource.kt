package com.alpersevindik.yoyocinema.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

internal abstract class NetworkBoundResource<T> {
    private val result = MediatorLiveData<T>()
    fun execute(): MediatorLiveData<T> {
        val dbSource = loadFromDB()
        if (dbSource != null) {
            result.addSource(dbSource) { data ->
                data?.let { result.value = data }
                result.removeSource(dbSource)

                continueWithNetwork()
            }
        } else {
            continueWithNetwork()
        }
        return result
    }

    private fun continueWithNetwork() {
        val networkSource = fetchFromNetwork()
        result.addSource(networkSource) { networkData ->
            result.removeSource(networkSource)

            GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
                networkData?.let { recipes ->
                    insertToDB(recipes)
                }
                loadFromDB()?.let { dbSource ->
                    result.addSource(dbSource) { data ->
                        data?.let { result.value = data }
                    }
                }
            })
        }
    }

    protected abstract fun loadFromDB(): LiveData<T>?
    protected abstract fun fetchFromNetwork(): LiveData<T>
    protected abstract fun insertToDB(data: T)
}