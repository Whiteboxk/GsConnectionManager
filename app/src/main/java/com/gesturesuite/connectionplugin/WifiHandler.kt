package com.gesturesuite.connectionplugin

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import com.gesturesuite.connectionplugin.Utils.Companion.toast

class WifiHandler {
    companion object{
        const val ON = 1
        const val OFF = 2
        const val TOGGLE = 3

        public fun wifi(context: Context, action: Int, toast: Boolean) {
            val wifiManager = context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager?
            wifiManager?.let {
                when (action) {
                    ON ->  {
                        wifiManager.isWifiEnabled = true
                        if(toast){
                            toast(context, R.string.Wifi_on)
                        }
                    }
                    OFF ->  {
                        wifiManager.isWifiEnabled = false
                        if(toast) {
                            toast(context, R.string.Wifi_off)
                        }
                    }
                    TOGGLE -> {
                        val newEnabledState = !wifiManager.isWifiEnabled
                        wifiManager.isWifiEnabled = newEnabledState
                        if(toast){
                            toast(context, if (newEnabledState) R.string.Wifi_on else R.string.Wifi_off)
                        }
                    }
                }
            }
        }


    }
}