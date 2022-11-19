package com.aomine.directredapi

import android.app.Application
import com.aomine.directredapi.data.service.RedService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

open class RedApp:Application() {
    @Inject
    lateinit var redService: RedService

    override fun onCreate() {
        super.onCreate()
        RedDirect.instance = RedDirect(redService)
    }
}