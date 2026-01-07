# Google Maps Integration Setup Guide

This guide will help you set up Google Maps integration for the running/walking route tracking feature in SmartHealthTracker.

## Features Added

✅ **Google Maps Integration**: Real-time map display with user location tracking
✅ **Route Visualization**: Red dashed polyline showing the user's running/walking path
✅ **Live Tracking**: Real-time location updates during workout sessions
✅ **Workout Statistics**: Distance, duration, and pace tracking
✅ **Permission Handling**: Automatic location permission requests
✅ **Navigation Integration**: Easy access from the main dashboard

## Setup Instructions

### 1. Get Google Maps API Key

1. Go to the [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
   - **Maps SDK for Android** (Required)
   - **Places API** (Optional, for future features)
   - **Directions API** (Optional, for future features)

4. Create credentials:
   - Go to "Credentials" in the left sidebar
   - Click "Create Credentials" → "API Key"
   - Copy the generated API key

### 2. Configure API Key Restrictions (Recommended)

For security, restrict your API key:

1. In the Google Cloud Console, go to "Credentials"
2. Click on your API key
3. Under "Application restrictions", select "Android apps"
4. Add your app's package name: `com.smarthealthtracker`
5. Add your app's SHA-1 certificate fingerprint (see below)

#### Getting SHA-1 Fingerprint

**For Debug (Development):**
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

**For Release (Production):**
```bash
keytool -list -v -keystore smarthealthtracker-release-key.keystore -alias smarthealthtracker
```

### 3. Configure the App

1. **Copy the template file:**
   ```bash
   cp local.properties.template local.properties
   ```

2. **Edit `local.properties`:**
   ```properties
   GOOGLE_MAPS_API_KEY=your_actual_api_key_here
   ```

3. **Build the project:**
   ```bash
   ./gradlew build
   ```

## Usage

### Accessing the Running Map

1. Open the SmartHealthTracker app
2. From the main dashboard, tap the "Running" quick action button
3. Grant location permissions when prompted
4. Tap "Start Run" to begin tracking
5. Your route will be displayed as a red dashed line on the map
6. Tap "Stop Run" to end the session

### Features

- **Real-time Location Tracking**: GPS-based location updates every second
- **Route Visualization**: Red dashed polyline shows your path
- **Live Statistics**: 
  - Distance traveled
  - Workout duration
  - Average pace
- **Map Controls**: Zoom, compass, and my location button
- **Permission Handling**: Automatic permission requests with user-friendly UI

## Technical Details

### Dependencies Added

```kotlin
// Google Maps & Location
implementation("com.google.android.gms:play-services-maps:18.2.0")
implementation("com.google.android.gms:play-services-location:21.0.1")
implementation("com.google.maps.android:maps-compose:4.3.0")
implementation("com.google.maps.android:maps-ktx:3.4.0")
implementation("com.google.maps.android:maps-utils-ktx:3.4.0")
```

### Permissions Required

The app already includes the necessary permissions in `AndroidManifest.xml`:
- `ACCESS_FINE_LOCATION`
- `ACCESS_COARSE_LOCATION`
- `ACCESS_BACKGROUND_LOCATION`

### Files Created/Modified

**New Files:**
- `RunningMapScreen.kt` - Main running map interface
- `GOOGLE_MAPS_SETUP_GUIDE.md` - This setup guide
- `local.properties.template` - API key template

**Modified Files:**
- `build.gradle.kts` - Added Google Maps dependencies
- `AndroidManifest.xml` - Added Google Maps API key configuration
- `HealthNavigation.kt` - Added running map navigation
- `ModernDashboardScreen.kt` - Added running map quick action button

## Troubleshooting

### Common Issues

1. **"Google Maps API key not found"**
   - Ensure `local.properties` exists and contains your API key
   - Verify the API key is correct and has proper restrictions

2. **"Location permission denied"**
   - Check that location permissions are granted in device settings
   - Ensure the app has access to location services

3. **Map not loading**
   - Verify internet connection
   - Check that Maps SDK for Android is enabled in Google Cloud Console
   - Ensure API key restrictions allow your app

4. **Location not updating**
   - Check GPS is enabled on the device
   - Ensure the app has background location permission
   - Try moving to an area with better GPS signal

### Testing

1. **Test on a real device** (GPS doesn't work well in emulators)
2. **Test in different locations** to ensure GPS accuracy
3. **Test permission flows** by denying and re-granting permissions
4. **Test background tracking** by switching apps during a workout

## Future Enhancements

Potential features to add in the future:
- **Workout History**: Save and view past running sessions
- **Route Sharing**: Share routes with friends
- **Pace Zones**: Visual indicators for different pace ranges
- **Elevation Profile**: Show elevation changes during the route
- **Voice Coaching**: Audio feedback during workouts
- **Social Features**: Compare routes with other users

## Support

If you encounter any issues:
1. Check this guide for common solutions
2. Verify your Google Cloud Console configuration
3. Test with a simple Google Maps implementation first
4. Check the Android logs for detailed error messages

---

**Note**: This integration uses the existing `LocationService` class which already had workout session tracking capabilities. The Google Maps integration enhances the user experience by providing visual feedback of the running route.
