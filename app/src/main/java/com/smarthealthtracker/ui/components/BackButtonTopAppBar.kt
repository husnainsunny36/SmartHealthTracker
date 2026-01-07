package com.smarthealthtracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/**
 * BACK BUTTON TOP APP BAR
 * 
 * A reusable TopAppBar component that includes a back button for navigation.
 * This component provides consistent navigation behavior across the app.
 * 
 * KEY FEATURES:
 * - Back button with arrow icon
 * - Customizable title and colors
 * - Consistent styling across the app
 * - Proper navigation handling
 * 
 * USAGE:
 * - Use on screens that are navigated to from other screens
 * - Provides intuitive back navigation
 * - Maintains consistent UI/UX
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = contentColor
                )
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
        ),
        modifier = modifier
    )
}

/**
 * HEALTH THEMED BACK BUTTON TOP APP BAR
 * 
 * A specialized version of the BackButtonTopAppBar with health app theming.
 * Uses the app's health color scheme for better visual consistency.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthBackButtonTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    healthColor: Color = MaterialTheme.colorScheme.primary,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = healthColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = healthColor
                )
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = healthColor,
            navigationIconContentColor = healthColor,
            actionIconContentColor = healthColor
        ),
        modifier = modifier
    )
}
