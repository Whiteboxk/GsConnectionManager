package com.gesturesuite.wifiplugin

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class PermissionAsker: Activity() {

    companion object{
        const val PERMISSION="PERMISSION"
        const val DIALOG_TEXT="DIALOG_TEXT"

        fun getIntent(context: Context, permission: String, dialogText: String): Intent {
            return Intent(context, PermissionAsker::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(PERMISSION, permission)
                .putExtra(DIALOG_TEXT, dialogText)
        }
    }

    val requestCode = 12

    var permission = ""
    var dialogText = ""

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permission = intent.getStringExtra(PERMISSION) ?: ""
        dialogText = intent.getStringExtra(DIALOG_TEXT) ?: ""
        requestPermission()
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf<String>(permission), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(Utils.checkPermission(this, permission)){
            finish()
        }
        else{
            if(Utils.wasPermissionPermanentlyDenied(this,permission)){
                AlertDialog.Builder(this)
                    .setMessage(dialogText)
                    .setPositiveButton("Go to settings"){ dialog , which ->
                        dialog.dismiss()
                        startActivity(Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", packageName, null)
                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    }.show()
            }
            else{
                requestPermission()
            }
        }
    }
}