package com.bird.example.pushnotifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.Keep
import com.bird.Bird

@Keep
class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v("MyBroadcastReceiver", "onReceive $intent")

        when (intent.action) {

            "com.bird.broadcast.NOTIFICATION" -> handleNotificationInteractionIntent(
                context,
                intent
            )
        }
    }


    private fun handleNotificationInteractionIntent(context: Context, intent: Intent) {
        Log.v("MyBroadcastReceiver", "handleNotificationInteractionIntent $intent")

        val bird = Bird(context)
        val notificationInteraction = bird.notifications.getNotificationInteraction(intent)
        Log.v("MyBroadcastReceiver", "notificationInteraction $notificationInteraction")
    }
}

