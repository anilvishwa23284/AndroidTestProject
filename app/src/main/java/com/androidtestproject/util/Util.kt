package com.androidtestproject.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.androidtestproject.MainApplication
import com.androidtestproject.R

class Util {
    companion object{
        const val BASE_URL = "https://picsum.photos/"
        const val IMAGE_VIEW_MAX_ROUNDING_RADIUS = Int.MAX_VALUE
        const val IMAGE_VIEW_SMALL_ROUNDING_RADIUS = 16


        fun isOnline(): Boolean {
            val cm = MainApplication.appInstance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val n = cm.activeNetwork
                if (n != null) {
                    val nc = cm.getNetworkCapabilities(n)
                    //It will check for both wifi and cellular network
                    return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
                return false
            } else {
                val netInfo = cm.activeNetworkInfo
                return netInfo != null && netInfo.isConnectedOrConnecting
            }
        }
        fun getRotedAnimation(context: Context): Animation {
            val animFadeIn: Animation =
                AnimationUtils.loadAnimation(context, R.anim.rotate_forward)
            return animFadeIn
        }
    }
}