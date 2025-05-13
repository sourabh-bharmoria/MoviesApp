package com.example.moviesapp

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val repository: MyRepository,private val apiKey: String, private val languageCode: String): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            Timber.d("load called with key: ${page}")
            Timber.d("API key: $apiKey")

            Timber.d("Calling API with page: $page")
            val response = repository.getMovieList(apiKey, page, languageCode)
            Timber.d("response code: ${response.code()}")
            Timber.d("response body: ${response.body()}")
//For fetching movies with retrofit
            val movies = response.body()?.results ?: emptyList()

//For fetching movies with ktor
   //        val movies = response.results ?: emptyList()

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Timber.e("API Call Failed: ${e.message}")
            LoadResult.Error(e)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}