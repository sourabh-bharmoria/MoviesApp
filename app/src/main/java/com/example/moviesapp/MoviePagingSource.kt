package com.example.moviesapp

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val repository: MyRepository,private val apiKey: String): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            Log.d("MoviePagingSource", "load called with key: ${page}")
            Log.d("MoviePagingSource", "API key: $apiKey")

            Log.d("MoviePagingSource", "Calling API with page: $page")
            val response = repository.getMovieList(apiKey, page)
            Log.d("MoviePagingSource", "response code: ${response.code()}")
            Log.d("MoviePagingSource", "response body: ${response.body()}")
            val movies = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("MoviePagingSource", "API Call Failed: ${e.message}", e)
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