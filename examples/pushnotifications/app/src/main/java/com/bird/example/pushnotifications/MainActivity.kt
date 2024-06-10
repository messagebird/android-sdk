package com.bird.example.pushnotifications

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bird.Bird
import com.bird.contact.models.ExternalIdentifier
import com.bird.contact.models.SignedIdentity
import com.bird.example.pushnotifications.ui.theme.PushNotificationsTheme
import kotlinx.coroutines.launch

class MainActivity : ObservableActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val bird = Bird(this, this)
        alwaysRegisterActivityLifecycleCallbacks(bird.lifeCycleCallbacks)
        super.onCreate(savedInstanceState)
        val activity = this
        val signedIdentity = "replace_with_user_signedIdentity"

        setContent {
            MainScreen(
                onLoginSignedIdentity = {
                    try {
                        bird.contact.identify(
                            SignedIdentity(
                                signedIdentity
                            )
                        )
                    } catch (e: Throwable) {
                        Log.v("MainScreen", "contact.identify(SignedIdentity) failed with $e")
                    }
                },
                onLoginExternalIdentifier = {
                    try {
                        bird.contact.identify(
                            ExternalIdentifier(
                                "externalid",
                                "987654321"
                            )
                        )
                    } catch (e: Throwable) {
                        Log.v("MainScreen", "contact.identify(ExternalIdentifier) failed with $e")
                    }
                },
                onLogout = {
                    try {
                        bird.contact.reset()
                    } catch (e: Throwable) {
                        Log.v("MainScreen", "contact reset failed with $e")
                    }
                },
                onRequestPushNotificationPermission = {
                    val result = bird.notifications.requestPermission(activity)
                    Log.v(TAG, "requestPermission result $result")
                },
            )
        }
    }
}

@Composable
fun MainScreen(
    onLoginSignedIdentity: suspend () -> Unit,
    onLoginExternalIdentifier: suspend () -> Unit,
    onLogout: suspend () -> Unit,
    onRequestPushNotificationPermission: suspend () -> Unit,
) {
    PushNotificationsTheme {
        val coroutineScope = rememberCoroutineScope()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Column {
                    Text(text = "Login")
                    Button(onClick =
                    { coroutineScope.launch { onLoginSignedIdentity() } }) {
                        Code("contact.identify(SignedIdentity)")
                    }
                    Button(onClick =
                    { coroutineScope.launch { onLoginExternalIdentifier() } }) {
                        Code("contact.identify(ExternalIdentifier)")
                    }
                }
                Column {
                    Text(text = "Logout")
                    Button(onClick = { coroutineScope.launch { onLogout() } }) {
                        Code("contact.reset()")
                    }
                }
                Column {
                    Text(text = "Push notifications")
                    Button(onClick =
                    { coroutineScope.launch { onRequestPushNotificationPermission() } }) {
                        Text("Ask For Permission")
                    }
                }
            }
        }
    }
}

@Composable
fun Code(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontFamily = FontFamily.Monospace,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        onLoginSignedIdentity = {},
        onLoginExternalIdentifier = {},
        onLogout = {},
        onRequestPushNotificationPermission = {}
    )
}