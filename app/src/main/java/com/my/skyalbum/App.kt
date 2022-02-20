package com.my.skyalbum

import android.app.Application
import com.my.skyalbum.widget.log.DebugLogTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var self: Application
        fun applicationContext(): Application {
            return self
        }
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugLogTree())
    }

}