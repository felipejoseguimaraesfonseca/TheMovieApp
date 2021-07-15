package com.example.themovieapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.databinding.FragmentSearchBinding
import com.example.themovieapp.viewmodel.SearchMoviesViewModel
import com.example.themovieapp.viewmodel.SearchSeriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchMoviesViewModel: SearchMoviesViewModel
    private lateinit var searchSeriesViewModel: SearchSeriesViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMoviesViewModel = ViewModelProvider(this).get(SearchMoviesViewModel::class.java)
        searchSeriesViewModel = ViewModelProvider(this).get(SearchSeriesViewModel::class.java)

        searchMoviesAndSeries()
    }

    private fun searchMoviesAndSeries() {
        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        searchMoviesViewModel.searchMovies(editable.toString())
                        searchSeriesViewModel.searchSeries(editable.toString())
                    }
                }
            }
        }
    }
}