package biz.ddroid.data.net

import android.net.ConnectivityManager

class ConnectionManagerImpl(private val connectivityManager: ConnectivityManager?) : ConnectionManager {
    //TODO: https://medium.com/@evanschepsiror/checking-androids-network-connectivity-with-network-callback-fdb8d24a920c
    override fun isNetworkAbsent(): Boolean {
        val netInfo = connectivityManager?.activeNetworkInfo
        return netInfo == null || !netInfo.isConnected
    }

}