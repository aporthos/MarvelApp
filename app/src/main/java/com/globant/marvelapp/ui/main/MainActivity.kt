package com.globant.marvelapp.ui.main

import androidx.core.view.isGone
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.globant.marvelapp.R
import com.globant.marvelapp.databinding.ActivityMainBinding
import com.globant.marvelapp.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun initializeView() {
        val navView = dataBinding().mainBottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_favourites))
        dataBinding().mainBottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_favourites -> navView.isGone = false
                else -> navView.isGone = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.mainBottomNavigationView).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}