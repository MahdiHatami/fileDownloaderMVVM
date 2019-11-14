package com.metis.downloader

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.nav_host_fragment
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {
  private val navController: NavController
    get() = findNavController(R.id.nav_host_fragment)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // use label in nav_host for setting fragments title
    navController.addOnDestinationChangedListener { _, _, _ ->
      toolbar.title = navController.currentDestination?.label
    }

    // add Up button
    NavigationUI.setupWithNavController(
      toolbar,
      NavHostFragment.findNavController(nav_host_fragment)
    )

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI.onNavDestinationSelected(
      item,
      navController
    )
      || super.onOptionsItemSelected(item)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }
}
