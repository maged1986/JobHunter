package com.magednan.jobhunter.ui.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.ActivityAuthBinding
import com.magednan.jobhunter.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() , ConnectivityReceiver.ConnectivityReceiverListener{
    private lateinit var controller: NavController
    private var message=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectivityReceiver() , IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        val binding: ActivityAuthBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_auth)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment?
        controller = navHostFragment!!.navController
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