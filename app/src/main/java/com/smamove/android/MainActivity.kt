
package com.smamove.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.smamove.android.navigation.AppNavigation
import com.smamove.android.ui.theme.SMAAndroidTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SMAAndroidTheme {
                AppNavigation()
            }
        }
    }
}
