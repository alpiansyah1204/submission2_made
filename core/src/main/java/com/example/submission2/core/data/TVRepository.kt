package com.example.submission2.core.data

import com.example.submission2.core.data.local.source.TVLocalDataSource
import com.example.submission2.core.data.remote.ApiResponse
import com.example.submission2.core.data.remote.source.TVRemoteDataSource
import com.example.submission2.core.domain.model.tv.RatingTVDomain
import com.example.submission2.core.domain.model.tv.ResultTVDomain
import com.example.submission2.core.domain.model.tv.TVDomain
import com.example.submission2.core.domain.repository.IRatingTVRepository
import com.example.submission2.core.domain.repository.IRepository
import com.example.submission2.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TVRepository(
    private val remoteDataSource: TVRemoteDataSource,
    private val localDataSource: TVLocalDataSource
) : IRepository<ApiResponse<ResultTVDomain>, TVDomain>, IRatingTVRepository {
    override suspend fun getPopularRemoteData(page: Int): Flow<ApiResponse<ResultTVDomain>> {
        return flow {
            remoteDataSource.getList(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultTVResponseToDomain(data)))
                        }
                    }
                    is ApiResponse.Empty -> {
                        emit( ApiResponse.Empty )
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error("Error"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNowPlayingRemoteData(page: Int): Flow<ApiResponse<ResultTVDomain>> {
        return flow {
            remoteDataSource.getOnAirList(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultTVResponseToDomain(data)))
                        }
                    }
                    is ApiResponse.Empty -> {
                        emit( ApiResponse.Empty )
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error("Error"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTopRatedRemoteData(page: Int): Flow<ApiResponse<ResultTVDomain>> {
        return flow {
            remoteDataSource.getTopRatedList(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultTVResponseToDomain(data)))
                        }
                    }
                    is ApiResponse.Empty -> {
                        emit( ApiResponse.Empty )
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error("Error"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailRemoteData(id: Int): Flow<ApiResponse<TVDomain>> {
        return flow{
            remoteDataSource.getDetail(id).collect{
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        emit(ApiResponse.Success(DataMapper.mapTVResponseToDomain(data)))
                    }
                    is ApiResponse.Empty -> {
                        emit( ApiResponse.Empty )
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error("Error"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteLocalData(): Flow<List<TVDomain>> {
        return flow{
            localDataSource.getDatas().collect {
                emit(DataMapper.mapTVLocalResponseToDomain(it))
            }
        }    }

    override fun getListDetailLocalData(): Flow<List<TVDomain>> {
        return flow{
            localDataSource.getDataDetail().collect {
                emit(DataMapper.mapTVLocalResponseToDomain(it))
            }
        }
    }

    override suspend fun insertFavoriteLocalData(movie: TVDomain) {
        localDataSource.insert(DataMapper.mapTVDomainToResponse(movie))
    }

    override suspend fun deleteFavoriteLocalData(movie: TVDomain) {
        localDataSource.delete(DataMapper.mapTVDomainToResponse(movie))
    }

    override suspend fun deleteAllLocalData() {
        localDataSource.deleteAll()
    }

    override suspend fun getRatingTV(id: Int): Flow<ApiResponse<RatingTVDomain>> {
        return flow {
            remoteDataSource.getRating(id).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        emit(ApiResponse.Success(DataMapper.mapRatingTVResponseToDomain(data)))
                    }
                    is ApiResponse.Empty -> {
                        emit( ApiResponse.Empty )
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error("Error"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}