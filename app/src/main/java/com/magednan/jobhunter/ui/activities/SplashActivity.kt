package com.magednan.jobhunter.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.magednan.jobhunter.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        lifecycleScope.launch{
           delay(200L)
        }
        if(haveConnection()== false){showNetworkDialog();}
        else {checkAuth();}
    }
    private fun haveConnection(): Boolean {
        var haveConnection = false
        val cm =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals("WIFI", ignoreCase = true)) if (ni.isConnected) {
                haveConnection = true
            }
            if (ni.typeName.equals("MOBILE", ignoreCase = true)) if (ni.isConnected) {
                haveConnection = true
            }
        }
        return haveConnection
    }
    private fun showNetworkDialog() {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage("You are not connected to the net.")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Connected",
            DialogInterface.OnClickListener { dialog, id ->
                if (haveConnection()) {
                    dialog.cancel()
                } else {
                    showNetworkDialog()
                }
            })
        builder1.setNegativeButton(
            "Exit",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                finish()
            })
        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }
    /**
     * to check auth user
     */
     fun checkAuth() {
        val mAuth = FirebaseAuth.getInstance()
        //[START auth_state_listener]
        mAuth.addAuthStateListener {
            val user = it.currentUser
            if (user != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
    }
}