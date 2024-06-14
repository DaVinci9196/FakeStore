package org.microg.gms.profile

import android.app.Application

object ContextProvider {
    lateinit var context: Application
        private set

    fun init(application: Application) {
        context = application
        DeviceProfileMgr.applyProfile()
    }
}