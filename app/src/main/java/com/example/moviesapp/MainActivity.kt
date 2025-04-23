package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // It makes the android classes(Activity,Fragment) ready to receive dependencies.
class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView

        val adapter = MyAdapter()//Instance of the pagingDataAdapter

        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(//passing the pagingDataAdapter instance to the recycleviewList
            header = LoaderAdapter(),//Also passing the progressBar loading using LoaderAdapter
            footer = LoaderAdapter()
        )
     //   recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val movieMargin = MovieMargin() //adding margin to the items in the recyclerView
        recyclerView.addItemDecoration(movieMargin)
        recyclerView.layoutManager = LinearLayoutManager(this)

 //       viewModel.moviesPagingFlow

//calling with retrofit
 //       viewModel.fetchMoviesWithRetrofit()
//
////calling with Ktor
//      viewModel.fetchMoviesWithKtor()

        viewModel.moviesPagingFlow.observe(this){movies ->
           // recyclerView.adapter = MyAdapter(movies)
            adapter.submitData(lifecycle, movies)
        }
    }
}
