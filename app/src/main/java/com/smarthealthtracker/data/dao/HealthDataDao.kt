package com.smarthealthtracker.data.dao

import androidx.room.*
import com.smarthealthtracker.data.model.HealthData
import kotlinx.coroutines.flow.Flow


@Dao
interface HealthDataDao {
    
    // ===== BASIC CRUD OPERATIONS =====
    
    /**
     * Get health data for a specific date
     * @param date Date in YYYY-MM-DD format
     * @return HealthData for the specified date, or null if not found
     */
    @Query("SELECT * FROM health_data WHERE date = :date")
    suspend fun getHealthDataByDate(date: String): HealthData?
    
    /**
     * Get health data for a specific date and user
     * @param date Date in YYYY-MM-DD format
     * @param userId User ID for data isolation
     * @return HealthData for the specified date and user, or null if not found
     */
    @Query("SELECT * FROM health_data WHERE date = :date AND userId = :userId")
    suspend fun getHealthDataByDateAndUser(date: String, userId: String): HealthData?
    
    /**
     * Get health data for a specific date as a Flow for reactive updates
     * @param date Date in YYYY-MM-DD format
     * @return Flow of HealthData for the specified date
     */
    @Query("SELECT * FROM health_data WHERE date = :date")
    fun getHealthDataByDateFlow(date: String): Flow<HealthData?>
    
    /**
     * Get health data for a specific date and user as a Flow for reactive updates
     * @param date Date in YYYY-MM-DD format
     * @param userId User ID for data isolation
     * @return Flow of HealthData for the specified date and user
     */
    @Query("SELECT * FROM health_data WHERE date = :date AND userId = :userId")
    fun getHealthDataByDateAndUserFlow(date: String, userId: String): Flow<HealthData?>
    
    /**
     * Get all health data ordered by date (newest first)
     * @return Flow of all health data for reactive UI updates
     */
    @Query("SELECT * FROM health_data ORDER BY date DESC")
    fun getAllHealthData(): Flow<List<HealthData>>
    
    /**
     * Get all health data for a specific user ordered by date (newest first)
     * @param userId User ID for data isolation
     * @return List of all health data for the specified user
     */
    @Query("SELECT * FROM health_data WHERE userId = :userId ORDER BY date DESC")
    suspend fun getAllHealthDataForUser(userId: String): List<HealthData>
    
    /**
     * Get health data between two dates for analytics
     * @param startDate Start date in YYYY-MM-DD format
     * @param endDate End date in YYYY-MM-DD format
     * @return Flow of health data within the date range
     */
    @Query("SELECT * FROM health_data WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getHealthDataBetweenDates(startDate: String, endDate: String): Flow<List<HealthData>>
    
    /**
     * Insert new health data record
     * @param healthData HealthData object to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthData(healthData: HealthData)
    
    /**
     * Update existing health data record
     * @param healthData HealthData object to update
     */
    @Update
    suspend fun updateHealthData(healthData: HealthData)
    
    /**
     * Delete health data record
     * @param healthData HealthData object to delete
     */
    @Delete
    suspend fun deleteHealthData(healthData: HealthData)
    
    /**
     * Delete all health data for a specific user
     * @param userId User ID for data isolation
     */
    @Query("DELETE FROM health_data WHERE userId = :userId")
    suspend fun deleteAllHealthDataForUser(userId: String)
    
    // ===== ANALYTICS QUERIES =====
    // These queries provide aggregated data for analytics and reporting
    
    /**
     * Get average steps between two dates
     * @param startDate Start date in YYYY-MM-DD format
     * @param endDate End date in YYYY-MM-DD format
     * @return Average steps for the date range
     */
    @Query("SELECT AVG(steps) FROM health_data WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAverageSteps(startDate: String, endDate: String): Float?
    
    /**
     * Get average water intake between two dates
     * @param startDate Start date in YYYY-MM-DD format
     * @param endDate End date in YYYY-MM-DD format
     * @return Average water intake for the date range
     */
    @Query("SELECT AVG(waterIntake) FROM health_data WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAverageWaterIntake(startDate: String, endDate: String): Float?
    
    /**
     * Get average sleep hours between two dates
     * @param startDate Start date in YYYY-MM-DD format
     * @param endDate End date in YYYY-MM-DD format
     * @return Average sleep hours for the date range
     */
    @Query("SELECT AVG(sleepHours) FROM health_data WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAverageSleepHours(startDate: String, endDate: String): Float?
    
    // ===== BULK OPERATIONS =====
    
    /**
     * Delete all health data records
     * Used for data reset functionality
     */
    @Query("DELETE FROM health_data")
    suspend fun deleteAllHealthData()
}
