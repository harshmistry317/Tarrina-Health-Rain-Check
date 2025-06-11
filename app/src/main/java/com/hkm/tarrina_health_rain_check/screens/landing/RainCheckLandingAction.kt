package com.hkm.tarrina_health_rain_check.screens.landing

sealed interface RainCheckLandingAction {
    data object OnButtonClick : RainCheckLandingAction
}