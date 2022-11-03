package com.example.johndeere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.johndeere.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // restrict in which fragments the bottom navigation shows up
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.homePage_frag || nd.id == R.id.logIn_frag || nd.id == R.id.signUp_frag || nd.id == R.id.changePassword_frag) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        // set up for the bottom navigation
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.mainPage_bottomNav -> {
                    navController.navigate(R.id.mainPage_frag)
                    true
                }

                R.id.search_bottomNav -> {
                    navController.navigate(R.id.search_frag)
                    true
                }

                R.id.quizPage_bottomNav -> {
                    navController.navigate(R.id.quizzes_frag)
                    true
                }

                R.id.profile_bottomNav -> {
                    navController.navigate(R.id.profile_frag)
                    true
                }

                else -> {false}
            }
        }
    }
}