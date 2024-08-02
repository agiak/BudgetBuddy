package com.example.mywallet.core.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Configuration
import com.example.features.rules.salary.domain.SalaryWorkerFactory
import com.example.mywallet.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: SalaryWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        subscribeToNotificationsChannel()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun subscribeToNotificationsChannel() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelsNames =
            resources.getStringArray(com.example.features.rules.R.array.notification_channels_names)
        val channelsIds =
            resources.getStringArray(com.example.features.rules.R.array.notification_channels_ids)
        val channelsDescriptions =
            resources.getStringArray(com.example.features.rules.R.array.notification_channels_descriptions)

        val availableChannels = ArrayList<NotificationChannel>().apply {
            channelsIds.forEachIndexed { index, channelID ->
                val channel = NotificationChannel(
                    channelID,
                    channelsNames[index],
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = channelsDescriptions[index]
                }
                add(channel)
            }
        }
        manager.createNotificationChannels(availableChannels)
    }
}
