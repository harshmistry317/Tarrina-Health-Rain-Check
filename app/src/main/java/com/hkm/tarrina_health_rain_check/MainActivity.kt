package com.hkm.tarrina_health_rain_check

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hkm.tarrina_health_rain_check.screens.landing.RainCheckLandingRoot
import com.hkm.tarrina_health_rain_check.screens.landing.RainCheckLandingScreen
import com.hkm.tarrina_health_rain_check.ui.theme.TarrinaHealthRainCheckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarrinaHealthRainCheckTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RainCheckLandingRoot()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TarrinaHealthRainCheckTheme {
        Greeting("Android")
    }
}