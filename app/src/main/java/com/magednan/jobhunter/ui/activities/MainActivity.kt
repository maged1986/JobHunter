package com.magednan.jobhunter.ui.activities

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.ActivityMainBinding
import com.magednan.jobhunter.firebase.Firebase
import com.magednan.jobhunter.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebase: Firebase
    private var message=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_movie) as NavHostFragment?
        NavigationUI.setupWithNavController(
                binding.bottomNavigationView,
                navHostFragment!!.navController)
        registerReceiver(ConnectivityReceiver() , IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_movie) as NavHostFragment?
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.op_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                val bundle=Bundle()
                bundle.putString("search item",s)
                navHostFragment?.findNavController()?.navigate(R.id.searchJobFragment,bundle)

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.getItemId() === R.id.op_menu_signout) {
        /*    firebase= Firebase()
           firebase.signOut(this)*/
            startActivity(Intent(this, AuthActivity::class.java))
            return true
        } else return super.onOptionsItemSelected(item)
    }

    override fun onNetworkConnectionChanger(isConnected: Boolean) {
        shoNetworkMessage(isConnected)
    }
    private fun shoNetworkMessage(isConnected: Boolean) {
        if (isConnected){
            message="You are Online "
            val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }else{
            message="You are Offline "
            val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
}