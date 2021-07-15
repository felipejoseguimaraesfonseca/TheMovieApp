package com.example.themovieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.themovieapp.R
import com.example.themovieapp.databinding.ActivityNavigationBinding
import com.example.themovieapp.view.fragments.*

class NavigationActivity : AppCompatActivity() {

    private val moviesFragment = MoviesFragment()
    private val seriesFragment = SeriesFragment()
    private val favoritesFragment = FavoritesFragment()
    private val searchFragment = SearchFragment()
    private val myAccountFragment = MyAccountFragment()

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(moviesFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_movies -> replaceFragment(moviesFragment)
                R.id.ic_series -> replaceFragment(seriesFragment)
                R.id.ic_favorites -> replaceFragment(favoritesFragment)
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_my_account -> replaceFragment(myAccountFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}
