package com.pharmacy.crack.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class NetworkConnectivity(private val connectivity:ConnectivityManager) : LiveData<Boolean>() {
    constructor(application: Application):this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    var netWorkCallback= object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true) // function of live data to update live dat
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        super.onActive()
        val builder = NetworkRequest.Builder()
        connectivity.registerNetworkCallback(builder.build(),netWorkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        connectivity.unregisterNetworkCallback(netWorkCallback)
    }
}