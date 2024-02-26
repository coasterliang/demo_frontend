package org.example.hsbcbookfrontend

import android.app.Application
import org.example.hsbcbookfrontend.network.ApiManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiManager.initRxHttpUtil(this)
    }
}