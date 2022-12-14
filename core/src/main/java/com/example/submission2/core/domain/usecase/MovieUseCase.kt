package com.example.submission2.core.domain.usecase

import com.example.submission2.core.data.MovieRepository
import com.example.submission2.core.data.remote.ApiResponse
import com.example.submission2.core.presentation.model.movie.Movie
import com.example.submission2.core.presentation.model.movie.ResultMovie
import com.example.submission2.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieUseCase(private val repository: MovieRepository) :
    IUseCase<ApiResponse<ResultMovie>, Movie> {
    override suspend fun getPopularRemoteData(page: Int): Flow<ApiResponse<ResultMovie>> {
        return flow {
            repository.getPopularRemoteData(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultMovieDomainToPresentation(data)))
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

    override suspend fun getNowPlayingRemoteData(page: Int): Flow<ApiResponse<ResultMovie>> {
        return flow {
            repository.getNowPlayingRemoteData(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultMovieDomainToPresentation(data)))
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

    override suspend fun getTopRatedRemoteData(page: Int): Flow<ApiResponse<ResultMovie>> {
        return flow {
            repository.getTopRatedRemoteData(page).collect {
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        if (data.result.isNotEmpty()) {
                            emit(ApiResponse.Success(DataMapper.mapResultMovieDomainToPresentation(data)))
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

    override suspend fun getDetailRemoteData(id :Int): Flow<ApiResponse<Movie>> {
        return flow{
            repository.getDetailRemoteData(id).collect{
                when(it){
                    is ApiResponse.Success -> {
                        val data = it.data
                        emit(ApiResponse.Success(DataMapper.mapMovieRemoteDomainToPresentation(data)))
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

    override fun getFavoriteLocalData(): Flow<List<Movie>> {
        return flow{
            repository.getFavoriteLocalData().collect{
                emit(DataMapper.mapMovieLocalDomainToPresentation(it))
            }
        }
    }

    override fun getListDetailLocalData(): Flow<List<Movie>> {
        return flow{
            repository.getListDetailLocalData().collect {
                emit(DataMapper.mapMovieLocalDomainToPresentation(it))
            }
        }
    }

    override suspend fun insertFavoriteLocalData(movie: Movie) {
        repository.insertFavoriteLocalData(DataMapper.mapMovieRemotePresentationToDomain(movie))
    }

    override suspend fun deleteFavoriteLocalData(movie: Movie) {
        repository.deleteFavoriteLocalData(DataMapper.mapMovieRemotePresentationToDomain(movie))
    }

    override suspend fun deleteAllLocalData() {
        repository.deleteAllLocalData()
    }

}