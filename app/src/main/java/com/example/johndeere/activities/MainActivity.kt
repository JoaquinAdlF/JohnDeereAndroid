package com.example.johndeere.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.johndeere.R
import com.example.johndeere.databinding.ActivityMainBinding

/*
Main Activity: ünica actividad de todo el proyecto, dado que está basado en
el uso de fragmentos.
En esta actividad se define la nevegación a través de un "grafo de navegación" y de un menú.
*/
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Restricción para los fragmentos en los que el menú se muestra
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.homePage_frag || nd.id == R.id.logIn_frag || nd.id == R.id.signUp_frag || nd.id == R.id.changePassword_frag) {
                binding.bottomNav.visibility = View.GONE




            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        // Navegación del menú
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.mainPage_bottomNav -> {
                    navController.navigate(R.id.mainPage_frag)
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