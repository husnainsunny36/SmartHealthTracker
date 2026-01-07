# Testing Guide - Google Maps Running Tracker

## 📱 Installation

### Option 1: Direct APK Installation (Recommended)
1. The APK has been built successfully: `app/build/outputs/apk/debug/app-debug.apk` (49MB)
2. Connect your Android device via USB
3. Enable USB Debugging on your device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times to enable Developer Options
   - Go to Settings → Developer Options
   - Enable "USB Debugging"
4. Run the installation command:
   ```bash
   cd /Users/airtechsolutions/SmartHealthTracker
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

### Option 2: Android Studio
1. Open the project in Android Studio
2. Click the "Run" button (green play icon)
3. Select your connected device or emulator

### Option 3: Manual Transfer
1. Copy `app/build/outputs/apk/debug/app-debug.apk` to your device
2. Open the APK file on your device
3. Allow "Install from Unknown Sources" if prompted
4. Install the app

## 🗺️ Testing the Running Map Feature

### Important Notes Before Testing

1. **Google Maps API Key Required**: 
   - The map will not load with the dummy API key
   - You need to get a real API key from [Google Cloud Console](https://console.cloud.google.com/)
   - Follow the instructions in `GOOGLE_MAPS_SETUP_GUIDE.md`
   - Update `local.properties` with your API key
   - Rebuild the app after updating the API key

2. **Location Services Required**:
   - GPS must be enabled on your device
   - Test outdoors or near a window for best results
   - Emulators may not have accurate GPS

### Testing Steps

#### 1. Launch the App
- Open SmartHealthTracker on your device
- Sign in or create an account if needed

#### 2. Navigate to Running Map
- On the main dashboard, look for the **"Running"** quick action button
- It should be displayed in red with a running icon (DirectionsRun)
- Tap the "Running" button

#### 3. Grant Permissions
- When prompted, allow location permissions:
  - **Allow all the time** or **While using the app**
- This is required for GPS tracking

#### 4. Start a Run
- You should see a map interface with:
  - Google Maps view (if API key is configured)
  - "Start Run" button at the bottom
  - Your current location marker
- Tap the green **"Start Run"** button

#### 5. Track Your Route
- Start walking or running
- You should see:
  - A **red dashed line** appearing on the map showing your route
  - The map camera following your movement
  - Live statistics updating:
    - **Distance**: Shows meters or kilometers traveled
    - **Duration**: Shows elapsed time
    - **Pace**: Shows your current pace (minutes/km)

#### 6. Stop the Run
- When finished, tap the red **"Stop Run"** button
- Your workout session will end
- (Note: Current version doesn't save history, but this can be added)

### Expected Behavior

✅ **What Should Work:**
- Permission requests appear correctly
- Map loads and displays your location (with valid API key)
- Route appears as a red dashed polyline
- Statistics update in real-time
- Map follows your position
- Start/Stop buttons toggle correctly

❌ **Known Limitations:**
- Map won't load without a valid Google Maps API key
- GPS accuracy depends on device and location
- Battery usage may be higher during active tracking
- Workout history is not saved (future enhancement)

## 🐛 Troubleshooting

### Map Not Loading
**Symptom:** Blank screen where map should be

**Solutions:**
1. Verify you have a valid Google Maps API key
2. Check that Maps SDK for Android is enabled in Google Cloud Console
3. Ensure API key restrictions allow your app package name
4. Check internet connection

### Location Not Updating
**Symptom:** Map shows location but doesn't track movement

**Solutions:**
1. Ensure GPS is enabled on device
2. Test outdoors for better GPS signal
3. Check location permissions are granted
4. Try restarting the app

### "Permission Denied" Error
**Symptom:** Cannot access location

**Solutions:**
1. Go to device Settings → Apps → SmartHealthTracker
2. Grant location permissions
3. Choose "Allow all the time" or "While using the app"
4. Restart the app

### Route Line Not Appearing
**Symptom:** Location updates but no red line

**Solutions:**
1. Walk at least 10-20 meters to see the line
2. Ensure tracking has started (check button says "Stop Run")
3. Check GPS signal strength

## 📊 Testing Checklist

- [ ] App installs successfully
- [ ] Dashboard loads and displays correctly
- [ ] "Running" quick action button is visible
- [ ] Tapping "Running" navigates to map screen
- [ ] Location permission dialog appears
- [ ] Granting permissions allows map to load
- [ ] Map displays current location
- [ ] "Start Run" button works
- [ ] Route polyline appears as you move
- [ ] Statistics update in real-time
- [ ] "Stop Run" button ends the session
- [ ] Back button returns to dashboard

## 🎯 Test Scenarios

### Scenario 1: First Time User
1. Install app
2. Complete onboarding
3. Navigate to Running
4. Grant permissions when requested
5. Verify map loads correctly

### Scenario 2: Active Workout
1. Start a run
2. Walk for 100 meters
3. Verify route line appears
4. Check statistics are accurate
5. Stop the run

### Scenario 3: Permission Handling
1. Deny location permission
2. Verify helpful error message appears
3. Grant permission from settings
4. Verify app works after granting permission

### Scenario 4: Background Handling
1. Start a run
2. Switch to another app
3. Return to SmartHealthTracker
4. Verify tracking continues

## 📸 What to Look For

### Dashboard
- New "Running" button with red color and running icon
- Button should be between "Sleep" and "Reports"

### Running Map Screen
- Google Maps view (if API key configured)
- Blue dot showing current location
- Map controls (zoom, compass, my location button)
- Floating statistics card with Distance, Duration, Pace
- Large green "Start Run" button (or red "Stop Run" when active)
- Red dashed polyline showing your path

### Statistics Card
- Distance in meters or kilometers
- Duration in HH:MM:SS or MM:SS format
- Pace in MM:SS /km format
- Icons for each statistic

## 🔄 Next Steps After Testing

If everything works:
1. Get a real Google Maps API key
2. Update the API key in `local.properties`
3. Rebuild the app: `./gradlew assembleDebug`
4. Test with real outdoor running/walking

Future enhancements to add:
- Save workout history to database
- View past routes
- Export workout data
- Share routes with friends
- Elevation tracking
- Heart rate integration
- Voice coaching

## 📞 Support

If you encounter issues:
1. Check the logcat output: `adb logcat | grep SmartHealth`
2. Verify all steps in `GOOGLE_MAPS_SETUP_GUIDE.md`
3. Ensure device meets minimum requirements (API 24+)
4. Test with a different device if available

---

**Build Info:**
- APK Location: `app/build/outputs/apk/debug/app-debug.apk`
- Size: ~49MB
- Build Date: October 1, 2025
- Version: 1.0 (Debug Build)
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
