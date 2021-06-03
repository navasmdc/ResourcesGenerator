package com.gc.resourcesgenerator.demo

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Strings.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Strings.dispose()
    }
}