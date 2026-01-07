package com.smarthealthtracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.smarthealthtracker.data.model.*
import com.smarthealthtracker.data.dao.*

/**
 * HEALTH DATABASE
 * 
 * This is the main Room database for the Smart Health Tracker app.
 * It manages all health-related data storage using SQLite.
 * 
 * KEY FEATURES:
 * - Room database with SQLite backend
 * - Singleton pattern for database access
 * - Type converters for complex data types
 * - Multiple entities for different health metrics
 * - Thread-safe database operations
 * 
 * DATABASE STRUCTURE:
 * - health_data: Daily aggregated health metrics
 * - water_logs: Individual water intake entries
 * - step_logs: Individual step count entries
 * - sleep_logs: Individual sleep session entries
 * - user_goals: User-defined daily health goals
 * 
 * HOW IT WORKS:
 * 1. Database is created using Room.databaseBuilder
 * 2. Singleton pattern ensures single database instance
 * 3. DAOs provide type-safe database access
 * 4. Type converters handle complex data serialization
 * 5. Thread-safe operations using synchronized blocks
 * 
 * SECURITY:
 * - Database file is stored in app's private directory
 * - No external access to health data
 * - Backup rules exclude sensitive health data
 */
@Database(
    entities = [
        HealthData::class,      // Daily health metrics aggregation
        WaterLog::class,        // Individual water intake logs
        StepLog::class,         // Individual step count logs
        SleepLog::class,        // Individual sleep session logs
        UserGoals::class        // User-defined health goals
    ],
    version = 2,                // Database version for migrations (increased for userId fields)
    exportSchema = false        // Disable schema export for production
)
@TypeConverters(Converters::class)  // Custom type converters for complex data
abstract class HealthDatabase : RoomDatabase() {
    
    // ===== DAO ACCESS METHODS =====
    // These methods provide access to Data Access Objects (DAOs)
    // Each DAO handles CRUD operations for specific entities
    
    abstract fun healthDataDao(): HealthDataDao    // Daily health data operations
    abstract fun waterLogDao(): WaterLogDao        // Water intake logging
    abstract fun stepLogDao(): StepLogDao          // Step counting operations
    abstract fun sleepLogDao(): SleepLogDao        // Sleep tracking operations
    abstract fun userGoalsDao(): UserGoalsDao      // User goals management
    
    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null
        
        /**
         * SINGLETON DATABASE ACCESS
         * 
         * This method ensures only one database instance exists throughout the app lifecycle.
         * Uses double-checked locking pattern for thread safety.
         * 
         * @param context Application context for database creation
         * @param userId Optional user ID for user-specific database isolation
         * @return HealthDatabase instance
         */
        fun getDatabase(context: Context, userId: String? = null): HealthDatabase {
            val databaseName = if (userId != null) {
                "health_database_$userId"
            } else {
                "health_database"
            }
            
            return INSTANCE ?: synchronized(this) {
                // Create database instance using Room builder
                val instance = Room.databaseBuilder(
                    context.applicationContext,    // Use application context to prevent memory leaks
                    HealthDatabase::class.java,    // Database class
                    databaseName                    // User-specific database file name
                ).build()
                INSTANCE = instance
                instance
            }
        }
        
        /**
         * Clear the current database instance
         * This should be called when user logs out to ensure fresh database for next user
         */
        fun clearInstance() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
    }
}
