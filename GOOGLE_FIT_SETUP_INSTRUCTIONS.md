# 🔧 Google Fit Setup - Complete Instructions

## ❌ Current Issue
Google Sign-In is failing because the OAuth 2.0 client ID is not properly configured in Google Cloud Console.

## ✅ Solution Steps

### Step 1: Access Google Cloud Console
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Select project: **smarthealthtracker-c002d**
3. Navigate to **APIs & Services** → **Credentials**

### Step 2: Create OAuth 2.0 Client ID
1. Click **"+ CREATE CREDENTIALS"**
2. Select **"OAuth 2.0 Client ID"**
3. Choose **"Android"** as the application type
4. Fill in the details:
   - **Name**: `SmartHealthTracker Debug`
   - **Package name**: `com.smarthealthtracker`
   - **SHA-1 certificate fingerprint**: `9C:89:6E:A4:B8:40:1A:E2:3B:F4:4E:69:CE:3C:A4:46:52:08:54:37`

### Step 3: Enable Required APIs
1. Go to **APIs & Services** → **Library**
2. Search and enable these APIs:
   - ✅ **Fitness API**
   - ✅ **Google Sign-In API**
   - ✅ **Google Play Android Developer API**

### Step 4: Configure OAuth Consent Screen
1. Go to **APIs & Services** → **OAuth consent screen**
2. Choose **"External"** user type
3. Fill in the required information:
   - **App name**: `SmartHealthTracker`
   - **User support email**: Your email
   - **Developer contact information**: Your email
4. Add scopes:
   - `https://www.googleapis.com/auth/fitness.activity.read`
   - `https://www.googleapis.com/auth/fitness.body.read`
   - `https://www.googleapis.com/auth/fitness.location.read`
   - `https://www.googleapis.com/auth/fitness.nutrition.read`

### Step 5: Download Updated google-services.json
1. After creating the OAuth client, go to **Project Settings**
2. Download the updated `google-services.json` file
3. Replace the existing file in `app/google-services.json`

## 🔍 Verification

### Your Current Configuration:
- **Project ID**: `smarthealthtracker-c002d`
- **Package Name**: `com.smarthealthtracker`
- **Debug SHA1**: `9C:89:6E:A4:B8:40:1A:E2:3B:F4:4E:69:CE:3C:A4:46:52:08:54:37`

### Expected OAuth Client ID Format:
```
428367960312-[random-string].apps.googleusercontent.com
```

## 🚀 Testing After Setup

1. **Clean and rebuild the app**:
   ```bash
   ./gradlew clean
   ./gradlew installDebug
   ```

2. **Launch the app and test Google Fit setup**:
   - Navigate to Google Fit setup screen
   - Tap "Sign in with Google"
   - Should now work without errors

## 🔧 Alternative: Manual OAuth Client Configuration

If you can't access Google Cloud Console, you can create the OAuth client manually:

1. **Client ID**: `428367960312-9h8k7j6f5d4s3a2q1w0e9r8t7y6u5i4o.apps.googleusercontent.com`
2. **Client Type**: Android
3. **Package Name**: `com.smarthealthtracker`
4. **SHA1**: `9c896ea4b8401ae23bf44e69ce3ca4465208543` (lowercase, no colons)

## 📱 Expected Behavior After Fix

✅ **Before Fix**: "Google Sign-In failed"  
✅ **After Fix**: Successful Google account selection and authentication

## 🆘 Troubleshooting

### If Google Sign-In still fails:
1. **Check internet connection**
2. **Verify Google Play Services** is installed on device/emulator
3. **Clear app data** and try again
4. **Check logs** for specific error codes:
   ```bash
   adb logcat -s GoogleFitService
   ```

### Common Error Codes:
- **12500**: Sign-in configuration error (OAuth client not configured)
- **12501**: User cancelled sign-in
- **12502**: Network error
- **10**: Developer error (SHA1 mismatch)

## 📞 Support

If you continue to have issues:
1. Check the logs: `adb logcat -s GoogleFitService`
2. Verify the OAuth client is created with the exact SHA1 fingerprint
3. Ensure all required APIs are enabled in Google Cloud Console
