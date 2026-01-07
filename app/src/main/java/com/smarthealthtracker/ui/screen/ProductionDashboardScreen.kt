package com.smarthealthtracker.ui.screen

/**
 * PRODUCTION DASHBOARD SCREEN
 * 
 * This is the main dashboard screen that shows an overview of the user's health data.
 * It's the first screen users see after logging in.
 * 
 * KEY FEATURES:
 * - Health score calculation and display
 * - Quick action cards for water, steps, and sleep tracking
 * - Today's progress overview
 * - Animated UI elements for better user experience
 * - Navigation to detailed tracking screens
 * 
 * HOW IT WORKS:
 * 1. Loads today's health data from the database
 * 2. Calculates overall health score based on goals vs actual
 * 3. Displays progress bars for each health metric
 * 4. Provides quick access buttons to detailed tracking screens
 * 5. Shows motivational messages based on progress
 * 
 * DATA FLOW:
 * HealthViewModel -> Database -> UI Components
 * 
 * ANIMATIONS:
 * - Header fades in on load
 * - Health score card slides in
 * - Quick action cards scale on press
 */

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smarthealthtracker.ui.theme.*
import com.smarthealthtracker.ui.viewmodel.HealthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductionDashboardScreen(
    onNavigateToWater: () -> Unit,
    onNavigateToSteps: () -> Unit,
    onNavigateToSleep: () -> Unit,
    onNavigateToGoals: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToConnectionStatus: () -> Unit = {},
    onSignOut: () -> Unit = {},
    viewModel: HealthViewModel = hiltViewModel()
) {
    val healthData by viewModel.todayHealthData.collectAsState()
    val userGoals by viewModel.userGoals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val connectedWearables by viewModel.connectedWearables.collectAsState()
    val isWearableSyncInProgress by viewModel.isWearableSyncInProgress.collectAsState()
    
    // Google Fit connection status
    var isGoogleFitConnected by remember { mutableStateOf(false) }
    var googleFitStatus by remember { mutableStateOf("Checking...") }
    
    // Coroutine scope for handling suspend functions
    val coroutineScope = rememberCoroutineScope()
    
    // Check Google Fit connection status
    LaunchedEffect(Unit) {
        isGoogleFitConnected = viewModel.isGoogleFitAvailable()
        googleFitStatus = if (isGoogleFitConnected) "Connected" else "Not Connected"
    }
    
    // Animation states
    val headerAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0.7f else 1f,
        animationSpec = tween(300),
        label = "headerAlpha"
    )
    
    val contentScale by animateFloatAsState(
        targetValue = if (isLoading) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "contentScale"
    )
    
    // Data is now automatically loaded via reactive flow
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scale(contentScale),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(600, easing = EaseOutCubic)
                ) + fadeIn(animationSpec = tween(600))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(headerAlpha),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Health Dashboard",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(
                        onClick = onSignOut,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        item {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(600, delayMillis = 200, easing = EaseOutCubic)
                ) + fadeIn(animationSpec = tween(600, delayMillis = 200))
            ) {
                HealthScoreCard(
                    score = healthData?.healthScore ?: 0,
                    isLoading = isLoading
                )
            }
        }
        
        item {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val quickActions = listOf(
                    QuickAction("Add Water", Icons.Default.LocalDrink, HealthBlue, onNavigateToWater),
                    QuickAction("Log Steps", Icons.Default.DirectionsWalk, HealthGreen, onNavigateToSteps),
                    QuickAction("Sleep Log", Icons.Default.Bedtime, HealthPurple, onNavigateToSleep),
                    QuickAction("Set Goals", Icons.Default.Flag, HealthOrange, onNavigateToGoals)
                )
                items(quickActions) { action ->
                    QuickActionCard(
                        title = action.title,
                        icon = action.icon,
                        color = action.color,
                        onClick = action.onClick
                    )
                }
            }
        }
        
        item {
            Text(
                text = "Today's Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val steps = healthData?.steps ?: 0
                val water = healthData?.waterIntake ?: 0
                val sleep = healthData?.sleepHours ?: 0f
                
                val stepGoal = userGoals?.dailySteps ?: 10000
                val waterGoal = userGoals?.dailyWater ?: 2000
                val sleepGoal = userGoals?.dailySleep ?: 8f
                
                val summaryCards = listOf(
                    SummaryCardData(
                        title = "Steps",
                        value = steps.toString(),
                        unit = "steps",
                        progress = (steps.toFloat() / stepGoal).coerceAtMost(1f),
                        color = HealthGreen,
                        icon = Icons.Default.DirectionsWalk,
                        onClick = onNavigateToSteps
                    ),
                    SummaryCardData(
                        title = "Water",
                        value = "${water}ml",
                        unit = "intake",
                        progress = (water.toFloat() / waterGoal).coerceAtMost(1f),
                        color = HealthBlue,
                        icon = Icons.Default.LocalDrink,
                        onClick = onNavigateToWater
                    ),
                    SummaryCardData(
                        title = "Sleep",
                        value = "${String.format("%.1f", sleep)}h",
                        unit = "duration",
                        progress = (sleep / sleepGoal).coerceAtMost(1f),
                        color = HealthPurple,
                        icon = Icons.Default.Bedtime,
                        onClick = onNavigateToSleep
                    )
                )
                items(summaryCards) { card ->
                    SummaryCard(
                        title = card.title,
                        value = card.value,
                        unit = card.unit,
                        progress = card.progress,
                        color = card.color,
                        icon = card.icon,
                        onClick = card.onClick
                    )
                }
            }
        }
        
        item {
            DeviceConnectivityCard(
                connectedWearables = connectedWearables,
                isWearableSyncInProgress = isWearableSyncInProgress,
                isGoogleFitConnected = isGoogleFitConnected,
                googleFitStatus = googleFitStatus,
                onSyncWearables = { viewModel.syncWearableData() },
                onSyncGoogleFit = { 
                    coroutineScope.launch {
                        viewModel.syncWithGoogleFit()
                    }
                },
                onRefreshConnections = {
                    coroutineScope.launch {
                        isGoogleFitConnected = viewModel.isGoogleFitAvailable()
                        googleFitStatus = if (isGoogleFitConnected) "Connected" else "Not Connected"
                        viewModel.refreshWearableDevices()
                    }
                },
                onViewDetails = onNavigateToConnectionStatus
            )
        }
        
        item {
            AIInsightsCard(
                healthData = healthData,
                userGoals = userGoals
            )
        }
        
        if (errorMessage != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = HealthRed.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = HealthRed
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = errorMessage!!,
                            color = HealthRed,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.clearError() }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HealthScoreCard(
    score: Int,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                score >= 80 -> HealthGreen.copy(alpha = 0.1f)
                score >= 60 -> HealthOrange.copy(alpha = 0.1f)
                else -> HealthRed.copy(alpha = 0.1f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        score >= 80 -> HealthGreen
                        score >= 60 -> HealthOrange
                        else -> HealthRed
                    }
                )
                Text(
                    text = "Health Score",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when {
                        score >= 80 -> "Excellent! Keep it up!"
                        score >= 60 -> "Good progress, almost there!"
                        else -> "Let's improve together!"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "cardScale"
    )
    
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(100.dp)
            .scale(scale),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    unit: String,
    progress: Float,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(160.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = color,
                trackColor = color.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun AIInsightsCard(
    healthData: com.smarthealthtracker.data.model.HealthData?,
    userGoals: com.smarthealthtracker.data.model.UserGoals?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = HealthPurple.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Psychology,
                    contentDescription = "AI Insights",
                    tint = HealthPurple,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "AI Insights",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = HealthPurple
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val insights = generateInsights(healthData, userGoals)
            insights.forEach { insight ->
                Text(
                    text = "• $insight",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

// Data classes
data class QuickAction(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

data class SummaryCardData(
    val title: String,
    val value: String,
    val unit: String,
    val progress: Float,
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)


fun generateInsights(
    healthData: com.smarthealthtracker.data.model.HealthData?,
    userGoals: com.smarthealthtracker.data.model.UserGoals?
): List<String> {
    val insights = mutableListOf<String>()
    
    if (healthData == null) {
        insights.add("Start your health journey by logging your first activity!")
        return insights
    }
    
    val stepGoal = userGoals?.dailySteps ?: 10000
    val waterGoal = userGoals?.dailyWater ?: 2000
    val sleepGoal = userGoals?.dailySleep ?: 8f
    
    // Step insights
    when {
        healthData.steps >= stepGoal -> insights.add("Great job! You've reached your step goal!")
        healthData.steps >= stepGoal * 0.8 -> insights.add("Almost there! Just ${stepGoal - healthData.steps} more steps to go.")
        else -> insights.add("Try to take a short walk to increase your step count.")
    }
    
    // Water insights
    when {
        healthData.waterIntake >= waterGoal -> insights.add("Excellent hydration! Keep drinking water throughout the day.")
        healthData.waterIntake >= waterGoal * 0.8 -> insights.add("Good hydration! Just ${waterGoal - healthData.waterIntake}ml more to reach your goal.")
        else -> insights.add("Remember to drink water regularly. Aim for ${waterGoal - healthData.waterIntake}ml more today.")
    }
    
    // Sleep insights
    when {
        healthData.sleepHours >= sleepGoal -> insights.add("Perfect sleep duration! You're well-rested.")
        healthData.sleepHours >= sleepGoal * 0.8 -> insights.add("Good sleep! Try to get ${String.format("%.1f", sleepGoal - healthData.sleepHours)} more hours tonight.")
        else -> insights.add("Consider going to bed earlier to get more rest. Aim for ${String.format("%.1f", sleepGoal - healthData.sleepHours)} more hours.")
    }
    
    return insights
}

@Composable
fun DeviceConnectivityCard(
    connectedWearables: List<String>,
    isWearableSyncInProgress: Boolean,
    isGoogleFitConnected: Boolean,
    googleFitStatus: String,
    onSyncWearables: () -> Unit,
    onSyncGoogleFit: () -> Unit,
    onRefreshConnections: () -> Unit,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = HealthBlue.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Devices,
                        contentDescription = "Device Connectivity",
                        tint = HealthBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Device Connectivity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = HealthBlue
                    )
                }
                
                IconButton(
                    onClick = onRefreshConnections,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh Connections",
                        tint = HealthBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Google Fit Connection Status - Hidden
            /*
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = "Google Fit",
                        tint = if (isGoogleFitConnected) HealthGreen else HealthRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Google Fit",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = googleFitStatus,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isGoogleFitConnected) HealthGreen else HealthRed,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            */
            
            // Wearable Devices Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Watch,
                        contentDescription = "Wearable Devices",
                        tint = if (connectedWearables.isNotEmpty()) HealthGreen else HealthRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Wearable Devices",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = if (connectedWearables.isNotEmpty()) "${connectedWearables.size} Connected" else "None",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (connectedWearables.isNotEmpty()) HealthGreen else HealthRed,
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Connected devices list
            if (connectedWearables.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Connected Devices:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                connectedWearables.forEach { device ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Connected",
                            tint = HealthGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = device,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Action buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Sync buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSyncWearables,
                        enabled = !isWearableSyncInProgress,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = HealthGreen)
                    ) {
                        if (isWearableSyncInProgress) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                Icons.Default.Sync,
                                contentDescription = "Sync Wearables",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sync Wearables")
                    }
                    
                    // Google Fit Sync Button - Hidden
                    /*
                    Button(
                        onClick = onSyncGoogleFit,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = HealthBlue)
                    ) {
                        Icon(
                            Icons.Default.FitnessCenter,
                            contentDescription = "Sync Google Fit",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sync Google Fit")
                    }
                    */
                }
                
                // View Details button
                Button(
                    onClick = onViewDetails,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = HealthPurple)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "View Details",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("View Connection Details")
                }
            }
        }
    }
}
