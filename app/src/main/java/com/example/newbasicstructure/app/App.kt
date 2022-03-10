package com.example.newbasicstructure.app

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import com.example.newbasicstructure.util.Constant.DEVELOPER_MODE
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by Jeetesh surana.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
        super.onCreate()
    }
}
