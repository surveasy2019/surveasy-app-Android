package com.surveasy.surveasy.presentation.util

import androidx.navigation.NavController
import com.surveasy.surveasy.NavGraphDirections

fun NavController.toMy() {
    navigate(NavGraphDirections.actionGlobalMyFragment())
}

fun NavController.toList() {
    navigate(NavGraphDirections.actionGlobalListFragment())
}