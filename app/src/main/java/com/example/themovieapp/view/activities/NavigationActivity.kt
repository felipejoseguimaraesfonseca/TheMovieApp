package com.example.themovieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.themovieapp.R
import com.example.themovieapp.view.fragments.FavoritesFragment
import com.example.themovieapp.view.fragments.MoviesFragment
import com.example.themovieapp.view.fragments.MyAccountFragment
import com.example.themovieapp.view.fragments.SeriesFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    private val moviesFragment = MoviesFragment()
    private val seriesFragment = SeriesFragment()
    private val favoritesFragment = FavoritesFragment()
    private val myAccountFragment = MyAccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        replaceFragment(moviesFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_movies -> replaceFragment(moviesFragment)
                R.id.ic_series -> replaceFragment(seriesFragment)
                R.id.ic_favorites -> replaceFragment(favoritesFragment)
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
