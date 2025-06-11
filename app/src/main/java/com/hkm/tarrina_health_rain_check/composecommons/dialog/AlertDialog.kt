package com.hkm.tarrina_health_rain_check.composecommons.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hkm.tarrina_health_rain_check.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    message: String,
    title: String = stringResource(R.string.rain_check),
    showNegativeButton: Boolean = false,
    positiveButtonText : String? = null,
    negativeButtonText : String? = null,
    onPositiveButtonClick: ()-> Unit,
    onNegativeButtonClick: () -> Unit = {}
) {
    BasicAlertDialog(
        onDismissRequest = {

        },
        modifier = Modifier,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        content = {
            Surface(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.W500,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        if (showNegativeButton) {
                            TextButton(
                                onClick = { onNegativeButtonClick() },
                                modifier = Modifier
                            ) {
                                Text(negativeButtonText?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    )

                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        TextButton(
                            onClick = { onPositiveButtonClick() },
                            modifier = Modifier
                        ) {
                            Text(positiveButtonText?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W500,
                                )
                        }

                    }

                }
            }
        }
    )
}