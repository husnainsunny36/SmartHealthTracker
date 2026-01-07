package com.smarthealthtracker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smarthealthtracker.ui.components.MultiOptionFAB
import com.smarthealthtracker.ui.navigation.BottomNavigationBar
import com.smarthealthtracker.ui.navigation.HealthNavigation
import com.smarthealthtracker.ui.screen.AuthScreen
import com.smarthealthtracker.ui.screen.SplashScreen
import com.smarthealthtracker.ui.screen.PremiumHomeScreen
import com.smarthealthtracker.ui.theme.SmartHealthTrackerTheme
import com.smarthealthtracker.ui.viewmodel.AuthViewModel
import com.smarthealthtracker.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val isHighContrast by themeViewModel.isHighContrast.collectAsState()
            
            SmartHealthTrackerTheme(
                darkTheme = isDarkTheme,
                highContrast = isHighContrast
            ) {
                val authViewModel: AuthViewModel = hiltViewModel()
                val user by authViewModel.user.collectAsState()
                
                var showSplash by remember { mutableStateOf(true) }
                
                when {
                    showSplash -> {
                        SplashScreen(
                            onSplashFinished = {
                                showSplash = false
                            }
                        )
                    }
                    
                    user != null -> {
                        // User is authenticated, show main app
                        val navController = rememberNavController()
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .navigationBarsPadding(),
                            contentWindowInsets = WindowInsets(0, 0, 0, 0),
                            bottomBar = {
                                BottomNavigationBar(
                                    currentRoute = currentRoute,
                                    onNavigate = { route ->
                                        if (currentRoute != route) {
                                            if (route == "dashboard") {
                                                // For home navigation, clear back stack and go to dashboard
                                                navController.navigate(route) {
                                                    popUpTo(0) {
                                                        inclusive = false
                                                    }
                                                    launchSingleTop = true
                                                }
                                            } else {
                                                // For other screens, use normal navigation
                                                navController.navigate(route) {
                                                    popUpTo("dashboard") {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        }
                                    }
                                )
                            },
                            floatingActionButton = {
                                MultiOptionFAB(
                                    onWaterClick = { 
                                        if (currentRoute != "water") {
                                            navController.navigate("water") {
                                                popUpTo("dashboard") {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    onStepsClick = { 
                                        if (currentRoute != "steps") {
                                            navController.navigate("steps") {
                                                popUpTo("dashboard") {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    onSleepClick = { 
                                        if (currentRoute != "sleep") {
                                            navController.navigate("sleep") {
                                                popUpTo("dashboard") {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                )
                            }
                        ) { innerPadding ->
                            val layoutDirection = LocalLayoutDirection.current
                            HealthNavigation(
                                navController = navController,
                                onSignOut = { authViewModel.signOut() },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            )
                        }
                    }
                    
                    else -> {
                        // User is not authenticated, show auth screen
                        AuthScreen(
                            onAuthSuccess = {
                                // Auth success is handled by the LaunchedEffect in AuthScreen
                            }
                        )
                    }
                }
            }
        }
    }
}
