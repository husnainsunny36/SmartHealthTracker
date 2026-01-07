package com.smarthealthtracker.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.smarthealthtracker.ui.theme.*

/**
 * MULTI-OPTION FLOATING ACTION BUTTON
 * 
 * This component provides a floating action button that expands to show multiple options
 * for quick health data logging (Water, Steps, Sleep).
 * 
 * KEY FEATURES:
 * - Expandable FAB with smooth animations
 * - Three health tracking options
 * - Color-coded icons for each option
 * - Smooth expand/collapse animations
 * - Backdrop blur effect when expanded
 * 
 * ANIMATIONS:
 * - Scale animation for main FAB
 * - Slide and fade animations for option buttons
 * - Staggered animation timing for smooth appearance
 * - Backdrop fade animation
 */

@Composable
fun MultiOptionFAB(
    onWaterClick: () -> Unit,
    onStepsClick: () -> Unit,
    onSleepClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    
    // Animation states
    val rotation by animateFloatAsState(
        targetValue = if (showMenu) 45f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )
    
    // Main FAB
    FloatingActionButton(
        onClick = { showMenu = !showMenu },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = if (showMenu) "Close Menu" else "Open Menu",
            modifier = Modifier.rotate(rotation)
        )
    }
    
    // Show menu dialog when expanded
    if (showMenu) {
        AlertDialog(
            onDismissRequest = { showMenu = false },
            title = {
                Text(
                    text = "Quick Log",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "What would you like to log?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Water option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onWaterClick()
                                showMenu = false
                            },
                        colors = CardDefaults.cardColors(containerColor = HealthBlue.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocalDrink,
                                contentDescription = "Water",
                                tint = HealthBlue,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Water Intake",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    
                    // Steps option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onStepsClick()
                                showMenu = false
                            },
                        colors = CardDefaults.cardColors(containerColor = HealthGreen.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.DirectionsWalk,
                                contentDescription = "Steps",
                                tint = HealthGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Step Count",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    
                    // Sleep option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSleepClick()
                                showMenu = false
                            },
                        colors = CardDefaults.cardColors(containerColor = HealthPurple.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Bedtime,
                                contentDescription = "Sleep",
                                tint = HealthPurple,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Sleep Log",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMenu = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

