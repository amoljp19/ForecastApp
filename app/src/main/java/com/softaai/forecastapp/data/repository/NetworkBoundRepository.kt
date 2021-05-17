package com.softaai.forecastapp.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.softaai.forecastapp.data.network.Resource
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<Resource<RESULT>> {

        emit(Resource.Success(fetchFromLocal().first()))

        val apiResponse = fetchFromRemote()

        val remotePages = apiResponse.body()

        if (apiResponse.isSuccessful && remotePages != null) {
            saveRemoteData(remotePages)
        } else {
            emit(Resource.Failed(apiResponse.message()))
        }

        emitAll(
            fetchFromLocal().map {
                Resource.Success<RESULT>(it)
            }
        )
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Failed("Network error! Can't get remote data."))
    }


    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)


    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>


    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}
