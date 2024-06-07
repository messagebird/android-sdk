package com.bird.example.pushnotifications

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bird.bird.BirdActivityLifecycleCallbackListenerInterface

open class ObservableActivity : ComponentActivity() {
    private val lifeCycleCallbacksList: ArrayList<BirdActivityLifecycleCallbackListenerInterface> = ArrayList<BirdActivityLifecycleCallbackListenerInterface>()

    fun alwaysRegisterActivityLifecycleCallbacks(callbacks: BirdActivityLifecycleCallbackListenerInterface) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(callbacks)
        } else {
            lifeCycleCallbacksList.add(callbacks)
        }
        addOnNewIntentListener {
            callbacks.onActivityNewIntent(this, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleCallbacksList.forEach { e -> e.onActivityCreated(this,savedInstanceState) }
    }


    override fun onPause() {
        super.onPause()
        lifeCycleCallbacksList.forEach { e -> e.onActivityPaused(this) }

    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleCallbacksList.forEach { e -> e.onActivityDestroyed(this) }
    }

    override fun onResume() {
        super.onResume()
        lifeCycleCallbacksList.forEach { e -> e.onActivityResumed(this) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            lifeCycleCallbacksList.forEach { e -> e.onActivityNewIntent(this, intent) }
        }

    }
    // TODO override the rest of method to call callbacks
}
