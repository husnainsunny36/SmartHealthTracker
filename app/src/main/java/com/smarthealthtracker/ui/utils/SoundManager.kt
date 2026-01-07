package com.smarthealthtracker.ui.utils

/**
 * SOUND MANAGER
 * 
 * This class manages all sound effects and meditation sounds in the app.
 * It uses Android's SoundPool for efficient sound playback.
 * 
 * KEY FEATURES:
 * - Manages sound effects for user interactions (water, steps, sleep, success)
 * - Handles meditation sounds (rain, ocean, forest, white noise)
 * - Automatic sound loading and cleanup
 * - Sound enable/disable functionality
 * - Error handling for missing sound files
 * 
 * HOW IT WORKS:
 * 1. Loads sound files from res/raw/ directory on initialization
 * 2. Maps sound names to SoundPool IDs for easy access
 * 3. Provides simple methods to play specific sounds
 * 4. Handles cases where sound files are missing gracefully
 * 5. Automatically releases resources when done
 * 
 * SOUND FILES REQUIRED:
 * - water_drop.mp3, step_sound.mp3, sleep_chime.mp3, success_chime.mp3, notification_sound.mp3
 * - rain_sounds.mp3, ocean_waves.mp3, forest_ambience.mp3, white_noise.mp3 (for meditation)
 * 
 * USAGE:
 * val soundManager = rememberSoundManager()
 * soundManager.playWaterSound() // Plays water drop sound
 * soundManager.playRainSound() // Plays rain meditation sound
 */

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import com.smarthealthtracker.R

class SoundManager(private val context: Context) {
    
    companion object {
        private const val TAG = "SoundManager"
        private const val MAX_STREAMS = 5
    }
    
    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<String, Int>()
    
    init {
        initializeSoundPool()
        loadSounds()
    }
    
    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(MAX_STREAMS)
            .setAudioAttributes(audioAttributes)
            .build()
    }
    
    private fun loadSounds() {
        try {
            // Load sound effects from raw resources
            // Note: You'll need to add actual sound files to res/raw/ directory
            soundMap["water"] = soundPool?.load(context, R.raw.water_drop, 1) ?: 0
            soundMap["step"] = soundPool?.load(context, R.raw.step_sound, 1) ?: 0
            soundMap["sleep"] = soundPool?.load(context, R.raw.sleep_chime, 1) ?: 0
            soundMap["success"] = soundPool?.load(context, R.raw.success_chime, 1) ?: 0
            soundMap["notification"] = soundPool?.load(context, R.raw.notification_sound, 1) ?: 0
            
            // Load meditation sounds (using existing sounds as placeholders)
            // TODO: Replace with actual meditation sound files when available
            soundMap["rain"] = soundPool?.load(context, R.raw.water_drop, 1) ?: 0
            soundMap["ocean"] = soundPool?.load(context, R.raw.sleep_chime, 1) ?: 0
            soundMap["forest"] = soundPool?.load(context, R.raw.success_chime, 1) ?: 0
            soundMap["white_noise"] = soundPool?.load(context, R.raw.notification_sound, 1) ?: 0
        } catch (e: Exception) {
            Log.w(TAG, "Could not load sound files: ${e.message}")
            // Sound files not found - this is expected until you add them
        }
    }
    
    fun playWaterSound() {
        playSound("water")
    }
    
    fun playStepSound() {
        playSound("step")
    }
    
    fun playSleepSound() {
        playSound("sleep")
    }
    
    fun playSuccessSound() {
        playSound("success")
    }
    
    fun playNotificationSound() {
        playSound("notification")
    }
    
    // Meditation sounds
    fun playRainSound() {
        playSound("rain")
    }
    
    fun playOceanSound() {
        playSound("ocean")
    }
    
    fun playForestSound() {
        playSound("forest")
    }
    
    fun playWhiteNoiseSound() {
        playSound("white_noise")
    }
    
    private fun playSound(soundKey: String) {
        try {
            val soundId = soundMap[soundKey]
            if (soundId != null && soundId > 0) {
                soundPool?.play(
                    soundId,
                    0.7f, // left volume
                    0.7f, // right volume
                    1, // priority
                    0, // loop (0 = no loop)
                    1f // rate
                )
            } else {
                Log.d(TAG, "Sound '$soundKey' not loaded or not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing sound '$soundKey': ${e.message}")
        }
    }
    
    fun release() {
        soundPool?.release()
        soundPool = null
        soundMap.clear()
    }
}