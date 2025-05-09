package com.example.moviesapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint // It makes the android classes(Activity,Fragment) ready to receive dependencies.
class MainActivity : AppCompatActivity() {

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel(this)

        setSupportActionBar(binding.toolBar)

        val drawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this,drawerLayout,binding.toolBar,R.string.OpenDrawer,R.string.CloseDrawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home ->{
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    binding.recyclerView.visibility = View.VISIBLE
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.favMovie -> {
                    // Handle navigation to Favourite Movies
                    val fragment = FavMovieFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,fragment)
                        .addToBackStack(null)
                        .commit()
                   // viewModel.movies
                    Toast.makeText(this,"FavouriteMovies", Toast.LENGTH_SHORT).show()
                    binding.recyclerView.visibility = View.GONE
                    true
                }
                R.id.notification -> {
                    // Handle navigation to Home
                    val fragment1 = NotificationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,fragment1)
                        .addToBackStack(null)
                        .commit()

                    Toast.makeText(this,"Notifications Scheduled", Toast.LENGTH_SHORT).show()
                    binding.recyclerView.visibility = View.GONE
                    true
                }
                R.id.location -> {
                    val fragment = LocationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,fragment)
                        .addToBackStack(null)
                        .commit()

                    Toast.makeText(this,"Location", Toast.LENGTH_SHORT).show()
                    binding.recyclerView.visibility = View.GONE
                    true
                }
                else -> false
            }

            drawerLayout.closeDrawer(GravityCompat.START)

             true
        }

       onBackPressedDispatcher.addCallback(this){
           if(drawerLayout.isOpen){
               drawerLayout.closeDrawer(GravityCompat.START)
           }else{
               onBackPressedDispatcher.onBackPressed()
           }
       }



        val recyclerView = binding.recyclerView

        val adapter = MyAdapter(viewModel)//Instance of the pagingDataAdapter

        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(//passing the pagingDataAdapter instance to the recycleviewList
            header = LoaderAdapter(),//Also passing the progressBar loading using LoaderAdapter
            footer = LoaderAdapter()
        )
     //   recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val movieMargin = MovieMargin() //adding margin to the items in the recyclerView
        recyclerView.addItemDecoration(movieMargin)
        recyclerView.layoutManager = LinearLayoutManager(this)


//calling with retrofit
 //       viewModel.fetchMoviesWithRetrofit()
//
////calling with Ktor
//      viewModel.fetchMoviesWithKtor()

//Added padding for different devices
        ViewCompat.setOnApplyWindowInsetsListener(binding.root){view,insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        viewModel.moviesPagingFlow.observe(this){movies ->
           // recyclerView.adapter = MyAdapter(movies)
            Timber.d("Submitting paging data to adapter $movies")
            adapter.submitData(lifecycle, movies)
        }


    }
}

//Creating NotificationChannel for Notifications
private fun createNotificationChannel(context: Context){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channel = NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Notification Channel Description"
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
