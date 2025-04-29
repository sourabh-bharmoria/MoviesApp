package com.example.moviesapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.FragmentFavMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavMovieFragment : Fragment() {
//Two binding variable are used to avoid memory leaks when using ViewBinding inside Fragments.
    private var _binding: FragmentFavMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by viewModels()
    private lateinit var favAdapter: FavMovieAdapter // your own adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favAdapter = FavMovieAdapter(viewModel)
        binding.favMovieContainer.adapter = favAdapter
        binding.favMovieContainer.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movies.observe(viewLifecycleOwner) { favList ->

            favAdapter.submitList(favList)
        }

//Handling back button functionality when pressed back intent takes us back to the mainActivity
// and finishes the current fragment so can't go back to it again by pressing back
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
