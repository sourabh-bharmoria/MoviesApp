package com.example.moviesapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel // Makes a ViewModel injectable via Hilt. It is used to inject dependencies like a repository.
class MyViewModel @Inject constructor(
    private val repository: MyRepository
): ViewModel() {

    val moviesPagingFlow = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MoviePagingSource(repository, BuildConfig.TMDB_API_KEY) }
    ).liveData.cachedIn(viewModelScope)
 //The pager object takes 2 parameters 1.config which specify the pageSize and maintained items
 //2.The pagingSource where the data is coming from
    //The cachedIn() operator makes the data stream shareable and caches the loaded data with the provided
    //viewModelScope.

}
//
//    private val _movies = MutableLiveData<List<Movie>>()
//    val movies: LiveData<List<Movie>> = _movies
//
//    fun fetchMoviesWithRetrofit(page: Int = 1){
//        viewModelScope.launch {
//           try {
//               Log.d("MyViewModel", "Fetched Movies...")
//               Log.d("MyViewModel", "Calling API with key: ${BuildConfig.TMDB_API_KEY}")
//
//
//               val response = repository.getMovieList(BuildConfig.TMDB_API_KEY,page)
//               Log.d("MyViewModel", "Response success: ${response.isSuccessful}")
//               Log.d("MyViewModel", "Response body: ${response.body()}")
//
//               if(response.isSuccessful){
//                   _movies.value = response.body()?.results
//                   Log.d("MyViewModel", "Fetched Movies With Retrofit: ${_movies.value}")
//
//               }
//           }catch (e: Exception){
//               Log.d("MyViewModel", "API Call Failed: ${e.message}", e)
//           }
//
//        }
//    }
//
//    fun fetchMoviesWithKtor(page: Int = 1){
//        viewModelScope.launch {
//            try {
//                val response = repository.getMoviesWithKtor(BuildConfig.TMDB_API_KEY,page)
//                _movies.value = response.results
//                Log.d("MyViewModel","Fetched Movies With Ktor: ${_movies.value}")
//            }catch (e: Exception){
//                Log.d("MyViewModel","KtorApi Call Failed: ${e.message}")
//            }
//        }
//    }
//
//}

//Recycler view
//load more list finish (load more)