# Meditation & Sleep Sounds Integration Guide

This guide explains how to add meditation and sleep sound files to the Smart Health Tracker app.

## Required Meditation Sound Files

Place the following sound files in `app/src/main/res/raw/`:

1. **rain_sounds.mp3** - Gentle rain sounds for meditation
2. **ocean_waves.mp3** - Ocean wave sounds for relaxation
3. **forest_ambience.mp3** - Forest nature sounds
4. **white_noise.mp3** - Calming white noise for sleep

## Sound File Guidelines

- **Format**: MP3 or OGG format recommended
- **Duration**: 10-60 minutes for continuous loop
- **Volume**: Normalize volume levels for consistency
- **Quality**: 44.1kHz sample rate, 16-bit depth minimum
- **Size**: Keep files under 5MB each for app size optimization
- **Loop**: Sounds should loop seamlessly for continuous playback

## Adding Sound Files

1. Place your meditation sound files in `app/src/main/res/raw/` directory
2. Ensure filenames match exactly with the names listed above
3. Build the project to include them in the APK
4. The SoundManager will automatically load and play these sounds

## Features

The meditation section includes:
- **4 Sound Options**: Rain, Ocean, Forest, White Noise
- **Play/Pause Controls**: Tap to start/stop meditation sounds
- **Timer Display**: Shows how long the sound has been playing
- **Visual Feedback**: Cards highlight when selected/playing
- **Sleep Integration**: Perfect for bedtime relaxation

## Usage

1. Navigate to the Sleep Tracking screen
2. Scroll down to the "Meditation & Sleep Sounds" section
3. Choose your preferred sound (Rain, Ocean, Forest, or White Noise)
4. Tap the card to start playing
5. The timer will show how long you've been listening
6. Tap again to stop the meditation

## Customization

To add more meditation sounds:
1. Add new sound files to `app/src/main/res/raw/`
2. Update the `meditationOptions` list in `SleepTrackingScreen.kt`
3. Add corresponding sound loading in `SoundManager.kt`
4. Rebuild the app

## Alternative

If you don't have custom meditation sound files, the app will work without them - the SoundManager will simply log that sounds are not available.
