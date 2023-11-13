package com.gesturesuite.connectionplugin

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import com.gesturesuite.connectionplugin.Utils.Companion.checkPermission
import com.gesturesuite.connectionplugin.Utils.Companion.toast

class BluetoothHandler {

    companion object{

        const val ON = 1
        const val OFF = 2
        const val TOGGLE = 3
        const val DISCOVERY_ON = 4
        const val DISCOVERY_OFF = 5
        const val DISCOVERY_TOGGLE = 6

       fun setBluetoothAdapterEnabled(
           context: Context,
           action: Int,
           toast: Boolean
       ) {
           if(Utils.isAndroid12ndAbove()){
               if(!checkPermission(context, Manifest.permission.BLUETOOTH_CONNECT)){
                   context.startActivity(
                       PermissionAsker.getIntent(
                           context,
                           Manifest.permission.BLUETOOTH_CONNECT,
                           context.getString(R.string.bluetooth_permission_dialog))
                   )
                   return
               }
           }

           val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
           if (mBluetoothAdapter == null) {
               toast(context, context.getString(R.string.Error_accessing_bluetooth_adapter))
               return
           }
           when (action) {
               ON ->setBluetoothAdapterEnabled(mBluetoothAdapter, true, context, toast)
               OFF ->setBluetoothAdapterEnabled(mBluetoothAdapter, false, context, toast)
               TOGGLE ->setBluetoothAdapterEnabled(mBluetoothAdapter, !mBluetoothAdapter.isEnabled, context, toast)
               DISCOVERY_ON ->setBluetoothAdapterDiscoveryMode(true, context, toast)
               DISCOVERY_OFF ->setBluetoothAdapterDiscoveryMode(false, context, toast)
               DISCOVERY_TOGGLE -> setBluetoothAdapterDiscoveryMode(mBluetoothAdapter.scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, context, toast)
           }
        }


        private fun setBluetoothAdapterEnabled(
            mBluetoothAdapter: BluetoothAdapter,
            state: Boolean,
            context: Context,
            toast: Boolean
        ) {

            try {
                if (state) {
                    mBluetoothAdapter.enable()
                } else {
                    mBluetoothAdapter.disable()
                }
            }
            catch(e:Exception){
                e.printStackTrace()
            }
            if(toast) {
                toast(
                    context,
                    context.getString(if (state) R.string.Bluetooth_on else R.string.Bluetooth_off)
                )
            }
        }


        private fun setBluetoothAdapterDiscoveryMode(state: Boolean, con: Context, toast: Boolean) {

            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (!state) {
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1)
            }
            if(toast){
                toast(
                    con,
                    con.getString(if(state)R.string.Bluetooth_Discoverable_on else R.string.Bluetooth_Discoverable_off,
                        state
                    )
                )
            }
            con.startActivity(discoverableIntent)
        }



    }

}