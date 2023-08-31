package com.gesturesuite.wifiplugin

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Utils {
    companion object{
        fun toast(context: Context, @StringRes stringResId: Int){
            Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_LONG).show()
        }
        fun toast(context: Context, text: String){
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

        fun checkPermission(con: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                con,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        fun wasPermissionPermanentlyDenied(act: Activity?, permission: String?): Boolean {
            return !ActivityCompat.shouldShowRequestPermissionRationale(act!!, permission!!)
        }

        fun isAndroid13ndAbove(): Boolean {
            return VERSION.SDK_INT >= 33;
        }

        fun isAndroid12ndAbove(): Boolean {
            return VERSION.SDK_INT >= Build.VERSION_CODES.S;
        }



    }
}