package com.example.submission2.core.domain.repository

import com.example.submission2.core.data.remote.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IRepository<T,Z> {
    suspend fun getPopularRemoteData(page: Int): Flow<T>
    suspend fun getNowPlayingRemoteData(page: Int): Flow<T>
    suspend fun getTopRatedRemoteData(page: Int): Flow<T>
    suspend fun getDetailRemoteData(id:Int): Flow<ApiResponse<Z>>
    fun getFavoriteLocalData(): Flow<List<Z>>
    fun getListDetailLocalData(): Flow<List<Z>>
    suspend fun insertFavoriteLocalData(movie : Z)
    suspend fun deleteFavoriteLocalData(movie : Z)
    suspend fun deleteAllLocalData()
}