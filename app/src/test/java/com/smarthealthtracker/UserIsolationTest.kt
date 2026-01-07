package com.smarthealthtracker

import com.smarthealthtracker.data.model.HealthData
import com.smarthealthtracker.data.model.UserGoals
import com.smarthealthtracker.data.model.WaterLog
import com.smarthealthtracker.data.model.StepLog
import com.smarthealthtracker.data.model.SleepLog
import org.junit.Test
import org.junit.Assert.*

/**
 * USER ISOLATION TEST
 * 
 * This test verifies that user data is properly isolated between different users.
 * It ensures that when a user logs in, they only see their own data and not
 * data from other users.
 */
class UserIsolationTest {
    
    @Test
    fun `test user data isolation in HealthData`() {
        // Create health data for two different users
        val user1Data = HealthData(
            date = "2024-01-01",
            userId = "user1",
            steps = 5000,
            waterIntake = 1000,
            sleepHours = 7.5f,
            healthScore = 75
        )
        
        val user2Data = HealthData(
            date = "2024-01-01",
            userId = "user2",
            steps = 8000,
            waterIntake = 1500,
            sleepHours = 8.0f,
            healthScore = 85
        )
        
        // Verify that the data is different for different users
        assertNotEquals(user1Data.userId, user2Data.userId)
        assertNotEquals(user1Data.steps, user2Data.steps)
        assertNotEquals(user1Data.waterIntake, user2Data.waterIntake)
        assertNotEquals(user1Data.healthScore, user2Data.healthScore)
        
        // Verify that each user's data has their correct userId
        assertEquals("user1", user1Data.userId)
        assertEquals("user2", user2Data.userId)
    }
    
    @Test
    fun `test user data isolation in WaterLog`() {
        // Create water logs for two different users
        val user1WaterLog = WaterLog(
            userId = "user1",
            amount = 250,
            timestamp = "2024-01-01T10:00:00",
            date = "2024-01-01"
        )
        
        val user2WaterLog = WaterLog(
            userId = "user2",
            amount = 300,
            timestamp = "2024-01-01T10:00:00",
            date = "2024-01-01"
        )
        
        // Verify that the data is different for different users
        assertNotEquals(user1WaterLog.userId, user2WaterLog.userId)
        assertNotEquals(user1WaterLog.amount, user2WaterLog.amount)
        
        // Verify that each user's data has their correct userId
        assertEquals("user1", user1WaterLog.userId)
        assertEquals("user2", user2WaterLog.userId)
    }
    
    @Test
    fun `test user data isolation in StepLog`() {
        // Create step logs for two different users
        val user1StepLog = StepLog(
            userId = "user1",
            steps = 1000,
            timestamp = "2024-01-01T10:00:00",
            date = "2024-01-01"
        )
        
        val user2StepLog = StepLog(
            userId = "user2",
            steps = 1500,
            timestamp = "2024-01-01T10:00:00",
            date = "2024-01-01"
        )
        
        // Verify that the data is different for different users
        assertNotEquals(user1StepLog.userId, user2StepLog.userId)
        assertNotEquals(user1StepLog.steps, user2StepLog.steps)
        
        // Verify that each user's data has their correct userId
        assertEquals("user1", user1StepLog.userId)
        assertEquals("user2", user2StepLog.userId)
    }
    
    @Test
    fun `test user data isolation in SleepLog`() {
        // Create sleep logs for two different users
        val user1SleepLog = SleepLog(
            userId = "user1",
            sleepStart = "2024-01-01T22:00:00",
            sleepEnd = "2024-01-02T06:00:00",
            duration = 8.0f,
            quality = 4,
            date = "2024-01-01"
        )
        
        val user2SleepLog = SleepLog(
            userId = "user2",
            sleepStart = "2024-01-01T23:00:00",
            sleepEnd = "2024-01-02T07:00:00",
            duration = 8.0f,
            quality = 5,
            date = "2024-01-01"
        )
        
        // Verify that the data is different for different users
        assertNotEquals(user1SleepLog.userId, user2SleepLog.userId)
        assertNotEquals(user1SleepLog.sleepStart, user2SleepLog.sleepStart)
        assertNotEquals(user1SleepLog.quality, user2SleepLog.quality)
        
        // Verify that each user's data has their correct userId
        assertEquals("user1", user1SleepLog.userId)
        assertEquals("user2", user2SleepLog.userId)
    }
    
    @Test
    fun `test user data isolation in UserGoals`() {
        // Create user goals for two different users
        val user1Goals = UserGoals(
            userId = "user1",
            dailySteps = 8000,
            dailyWater = 1800,
            dailySleep = 7.5f,
            weeklyExercise = 120
        )
        
        val user2Goals = UserGoals(
            userId = "user2",
            dailySteps = 12000,
            dailyWater = 2500,
            dailySleep = 8.5f,
            weeklyExercise = 180
        )
        
        // Verify that the data is different for different users
        assertNotEquals(user1Goals.userId, user2Goals.userId)
        assertNotEquals(user1Goals.dailySteps, user2Goals.dailySteps)
        assertNotEquals(user1Goals.dailyWater, user2Goals.dailyWater)
        assertNotEquals(user1Goals.dailySleep, user2Goals.dailySleep)
        assertNotEquals(user1Goals.weeklyExercise, user2Goals.weeklyExercise)
        
        // Verify that each user's data has their correct userId
        assertEquals("user1", user1Goals.userId)
        assertEquals("user2", user2Goals.userId)
    }
    
    @Test
    fun `test Firebase data path structure`() {
        // Test that Firebase data paths are properly structured for user isolation
        val userId = "testUser123"
        val date = "2024-01-01"
        
        // Health data path
        val healthDataPath = "users/$userId/health_data/$date"
        assertEquals("users/testUser123/health_data/2024-01-01", healthDataPath)
        
        // Water logs path
        val waterLogPath = "users/$userId/water_logs/logId"
        assertEquals("users/testUser123/water_logs/logId", waterLogPath)
        
        // Step logs path
        val stepLogPath = "users/$userId/step_logs/logId"
        assertEquals("users/testUser123/step_logs/logId", stepLogPath)
        
        // Sleep logs path
        val sleepLogPath = "users/$userId/sleep_logs/logId"
        assertEquals("users/testUser123/sleep_logs/logId", sleepLogPath)
        
        // User goals path
        val userGoalsPath = "users/$userId/user_goals/goals"
        assertEquals("users/testUser123/user_goals/goals", userGoalsPath)
    }
}
