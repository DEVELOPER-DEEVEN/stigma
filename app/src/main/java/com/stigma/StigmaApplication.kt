package com.stigma

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Main application class for STIGMA app
 * Initializes Hilt dependency injection and logging
 */
@HiltAndroidApp
class StigmaApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("StigmaApplication initialized")
    }
}
