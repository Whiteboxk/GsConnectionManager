package com.gesturesuite.wifiplugin

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.gesturesuite.wifiplugin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUiState()
        b = ActivityMainBinding.inflate(layoutInflater)

        b.requestBluetoothPermissionBtn.setOnClickListener {
            if (Utils.isAndroid12ndAbove()) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        setUiState()
    }

    private fun setUiState(){
        b.textTv.text = getString(
            if( Utils.isAndroid13ndAbove()) R.string.text_for_13_and_above else R.string.text_for_before_13
        )
        b.closeBtn.setOnClickListener{
            finish()
        }
        b.bluetoothLayout.isVisible = Utils.isAndroid13ndAbove()
        if (Utils.isAndroid12ndAbove()) {
            val hasPermission = Utils.checkPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            b.bluetoothPermissionNotGrantedLayout.isVisible = !hasPermission
            b.bluetoothPermissionGrantedTv.isVisible = hasPermission
        }
    }

    override fun onResume() {
        super.onResume()
        setUiState()
    }

}