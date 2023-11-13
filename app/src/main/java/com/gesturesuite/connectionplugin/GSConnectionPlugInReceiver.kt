package com.gesturesuite.connectionplugin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GsConnectionManagerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {
            val toast = intent.getBooleanExtra("1", true)
            if(it.action == context.getString(R.string.wifiIntentAction)){
                WifiHandler.wifi(
                    context,
                    it.getIntExtra(context.getString(R.string.WifiIntentActionExtra), -1),
                    toast
                )
            }
            else if(it.action == context.getString(R.string.bluetoothIntentAction)){
                BluetoothHandler.setBluetoothAdapterEnabled(
                    context,
                    it.getIntExtra(context.getString(R.string.BluetoothIntentActionExtra), -1),
                    toast
                )
            }
        }
    }
}