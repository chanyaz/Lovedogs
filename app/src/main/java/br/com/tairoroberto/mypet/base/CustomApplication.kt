package br.com.tairoroberto.mypet.base

import android.app.Application

import com.facebook.stetho.Stetho

/**
 * Created by tairo on 7/23/17.
 */

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
