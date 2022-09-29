package com.willor.albertsons_interview

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Application class for Dagger-Hilt compatability
 */
@HiltAndroidApp
class MainApplication : Application() {
}