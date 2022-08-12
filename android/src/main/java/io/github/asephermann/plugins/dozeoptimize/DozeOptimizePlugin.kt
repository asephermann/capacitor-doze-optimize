package io.github.asephermann.plugins.dozeoptimize

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin


@CapacitorPlugin(name = "DozeOptimize")
class DozeOptimizePlugin : Plugin() {

    @PluginMethod
    fun isIgnoringBatteryOptimizations(call: PluginCall) {

        val packageName: String = activity.applicationContext.packageName

        val ret = JSObject()

        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                val isIgnoring = pm.isIgnoringBatteryOptimizations(packageName)

                ret.put("isIgnoring", isIgnoring)
                ret.put("messages", isIgnoring.toString())
            } else {
                ret.put("isIgnoring", false)
                ret.put("messages", "BATTERY_OPTIMIZATIONS Not available.")
            }
        } catch (e: Exception) {
            ret.put("isIgnoring", false)
            ret.put("messages", "IsIgnoringBatteryOptimizations: failed N/A\n$e")
        }

        call.resolve(ret)
    }

    @PluginMethod
    fun requestOptimizationsMenu(call: PluginCall) {

        val packageName: String = activity.applicationContext.packageName

        val ret = JSObject()

        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                val intent = Intent()

                val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                val isIgnoring = pm.isIgnoringBatteryOptimizations(packageName)

                if (isIgnoring) {
                    intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                    ret.put("messages", "requested")
                }else{
                    intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    intent.data = Uri.parse("package:$packageName")

                    ret.put("messages", "Optimizations Requested Successfully")
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

                ret.put("isRequested", true)
            } else {
                ret.put("isRequested", false)
                ret.put("messages", "BATTERY_OPTIMIZATIONS Not available.")
            }
        } catch (e: Exception) {
            ret.put("isRequested", false)
            ret.put("messages", "RequestOptimizationsMenu: failed N/A\n$e")
        }

        call.resolve(ret)
    }

    @PluginMethod
    fun isIgnoringDataSaver(call: PluginCall) {
        val ret = JSObject()

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                var isIgnoring = false
                val connMgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                when (connMgr.restrictBackgroundStatus) {
                    RESTRICT_BACKGROUND_STATUS_ENABLED ->
                        // The app is whitelisted. Wherever possible,
                        // the app should use less data in the foreground and background.
                        isIgnoring = false
                    RESTRICT_BACKGROUND_STATUS_WHITELISTED, RESTRICT_BACKGROUND_STATUS_DISABLED ->                             // Data Saver is disabled. Since the device is connected to a
                        // metered network, the app should use less data wherever possible.
                        isIgnoring = true
                }
                ret.put("isIgnoring", isIgnoring)
                ret.put("messages", isIgnoring.toString())
            } else {
                ret.put("isIgnoring", false)
                ret.put("messages", "DATA_SAVER Not available.")
            }
        } catch (e: Exception) {
            ret.put("isIgnoring", false)
            ret.put("messages", "IsIgnoringDataSaver: failed N/A\n$e")
        }

        call.resolve(ret)
    }

    @PluginMethod
    fun requestDataSaverMenu(call: PluginCall) {

        val packageName: String = activity.applicationContext.packageName

        val ret = JSObject()

        try {
            val intent = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val connMgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                when (connMgr.restrictBackgroundStatus) {
                    RESTRICT_BACKGROUND_STATUS_ENABLED -> {// 3
                        // The app is whitelisted. Wherever possible,
                        // the app should use less data in the foreground and background.

                        intent.action = Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.data = Uri.parse("package:$packageName")
                        context.startActivity(intent)

                        ret.put("isRequested", true)
                        ret.put("messages", "requested")
                    }
                    RESTRICT_BACKGROUND_STATUS_WHITELISTED, // 2
                    RESTRICT_BACKGROUND_STATUS_DISABLED -> {// 1
                        // Data Saver is disabled. Since the device is connected to a
                        // metered network, the app should use less data wherever possible.

                        ret.put("isRequested", false)
                        ret.put("messages", "not requested")
                    }
                }
            } else {
                ret.put("isRequested", false)
                ret.put("messages", "DATA_SAVER Not available.")
            }
        } catch (e: Exception) {
            ret.put("isRequested", false)
            ret.put("messages", "RequestDataSaverMenu failed: N/A\n$e")
        }

        call.resolve(ret)
    }
}
