package com.hkm.tarrina_health_rain_check.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.hkm.tarrina_health_rain_check.composecommons.dialog.AlertDialog

@OptIn(ExperimentalPermissionsApi::class)
object PermissionUtils {

    @Composable
    fun RequestLocationPermission(
        onGranted: () -> Unit
    ) {
        val context = LocalContext.current
        val shouldShowDialog = remember { mutableStateOf(false) }

        val locationPermissions = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            onPermissionsResult = { result ->
                val allGranted = result.values.all { it }
                if (!allGranted) shouldShowDialog.value = true
            }
        )

        LaunchedEffect(Unit) {
            locationPermissions.launchMultiplePermissionRequest()
        }

        if (locationPermissions.allPermissionsGranted) {
            onGranted()
        } else if (shouldShowDialog.value) {
            val rationale = locationPermissions.permissions.any { it.status.shouldShowRationale }
            ShowPermissionDialog(
                context = context,
                rationale = rationale,
                onRequest = { locationPermissions.launchMultiplePermissionRequest() },
                onDismiss = { shouldShowDialog.value = false },
                permission = "Location"
            )
        }
    }

    @Composable
    private fun ShowPermissionDialog(
        context: android.content.Context,
        permission:String,
        rationale: Boolean,
        onRequest: () -> Unit,
        onDismiss: () -> Unit
    ) {
       AlertDialog(
            title = "Permission Required",
            message = "$permission permission is required to use this feature.",
            showNegativeButton = true,
            positiveButtonText = if (rationale) "Grant Permission" else "Open Settings",
            negativeButtonText = "Cancel",
            onPositiveButtonClick = {
                if (rationale) {
                    onRequest()
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
                onDismiss()
            },
            onNegativeButtonClick = {
                onDismiss()
            }
        )
    }
}