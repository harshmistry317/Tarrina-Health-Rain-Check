package com.hkm.tarrina_health_rain_check.screens.landing


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hkm.tarrina_health_rain_check.composecommons.GradientBackground
import com.hkm.tarrina_health_rain_check.composecommons.dialog.AlertDialog
import com.hkm.tarrina_health_rain_check.screens.landing.component.AnimatedAppIcon
import com.hkm.tarrina_health_rain_check.screens.landing.component.DateSelectionCard
import com.hkm.tarrina_health_rain_check.screens.landing.component.FeatureCard
import com.hkm.tarrina_health_rain_check.screens.landing.component.StartButton
import com.hkm.tarrina_health_rain_check.ui.theme.LightPrimary
import com.hkm.tarrina_health_rain_check.utils.PermissionUtils
import kotlinx.coroutines.delay
import java.util.Date


@Composable
fun RainCheckLandingRoot(
    viewModel: RainCheckLandingViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    RainCheckLandingScreen(
        state = state,
        onAction = {
            when(it){
                RainCheckLandingAction.OnButtonClick -> {
//                    PermissionUtils.RequestLocationPermission {
                        viewModel.onAction(it)
//                    }

                }
            }

        }
    )
    if (!state.errorText.isNullOrBlank()){
        AlertDialog(
            message = state.errorText ?: "",
            showNegativeButton = false,
            positiveButtonText = "Ok",
            negativeButtonText = null,
            onPositiveButtonClick = {
                viewModel.updateState(
                    RainCheckLandingState(
                        isLoading = false,
                        errorText = null
                    )
                )
            },
            onNegativeButtonClick = {

            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RainCheckLandingScreen(
    onAction: (RainCheckLandingAction) -> Unit,
    state: RainCheckLandingState
) {
    var selectedDate by remember {
        mutableStateOf(Date().apply { time})
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var hasLocationPermission by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Check initial permission status
    LaunchedEffect(Unit) {
//        hasLocationPermission = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
    }

//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
//                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
//    }

    PermissionUtils.RequestLocationPermission {
        hasLocationPermission = true
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.time,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = System.currentTimeMillis()
                val sevenDaysFromNow = today + (7 * 24 * 60 * 60 * 1000)
                return utcTimeMillis in today..sevenDaysFromNow
            }
        }
    )

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Icon with Pulse Animation
            AnimatedAppIcon()

            Spacer(modifier = Modifier.height(32.dp))

            // Title Section
            Text(
                text = "RainCheck",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your friendly weather companion.\nNever get caught in the rain again! 🌧️",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Date Selection Card
            DateSelectionCard(
                selectedDate = selectedDate,
                onCardClick = { showDatePicker = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Feature Cards
            FeatureCard(
                icon = Icons.Default.DateRange,
                title = "Future Forecast",
                description = "Check weather up to 7 days ahead"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureCard(
                icon = Icons.Default.Person,
                title = "Rain Alerts",
                description = "Know exactly when to bring an umbrella"
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Start Button
            StartButton(
                isLoading = state.isLoading,
                onClick = {

                        onAction(RainCheckLandingAction.OnButtonClick)


//                    LaunchedEffect(isLoading) {
//                        if (isLoading) {
//                            delay(1500)
//                            onNavigateToMain()
//                        }
//                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tap to start checking the weather ☀️",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = Date(millis)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK", color = LightPrimary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel", color = LightPrimary)
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = LightPrimary,
                        todayDateBorderColor = LightPrimary
                    )
                )
            }
        }
    }
}