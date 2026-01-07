# Google Fit API Setup Guide

This guide will help you configure Google Fit API with the proper SHA-1 keys for your SmartHealthTracker app.

## 🔑 **Your SHA-1 Keys**

### **Debug Keystore (Development)**
```
SHA-1: 9C:89:6E:A4:B8:40:1A:E2:3B:F4:4E:69:CE:3C:A4:46:52:08:54:37
SHA-256: BA:95:07:F8:D5:07:C7:6B:96:ED:6A:F0:FF:99:2B:03:1D:7E:AD:01:38:BA:2C:8F:E2:63:39:13:EA:65:21:84
```

### **Release Keystore (Production)**
```
SHA-1: AC:BB:8D:58:A2:F5:92:C3:7F:3B:79:77:A2:FC:09:C4:31:A6:07:63
SHA-256: 1F:D8:29:8D:20:88:96:04:E4:99:0E:2D:7C:EE:67:BF:76:96:1F:C2:22:85:7F:6B:AC:1C:C8:9A:F5:46:0C:AA
```

## 📋 **Step-by-Step Google Cloud Console Setup**

### **Step 1: Access Google Cloud Console**
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Select your project: `smarthealthtracker-c002d`
3. Navigate to **APIs & Services** → **Credentials**

### **Step 2: Create OAuth 2.0 Client ID**
1. Click **"+ CREATE CREDENTIALS"**
2. Select **"OAuth 2.0 Client ID"**
3. Choose **"Android"** as the application type
4. Fill in the details:
   - **Name**: `SmartHealthTracker Android Client`
   - **Package name**: `com.smarthealthtracker`
   - **SHA-1 certificate fingerprint**: 
     - For development: `9C:89:6E:A4:B8:40:1A:E2:3B:F4:4E:69:CE:3C:A4:46:52:08:54:37`
     - For production: `AC:BB:8D:58:A2:F5:92:C3:7F:3B:79:77:A2:FC:09:C4:31:A6:07:63`

### **Step 3: Enable Required APIs**
1. Go to **APIs & Services** → **Library**
2. Search and enable these APIs:
   - **Fitness API** (for Google Fit)
   - **Google Play Android Developer API** (if needed)
   - **Google Sign-In API** (for authentication)

### **Step 4: Configure OAuth Consent Screen**
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

### **Step 5: Download Updated google-services.json**
1. After creating the OAuth client, go to **Project Settings**
2. Download the updated `google-services.json` file
3. Replace the existing file in your project

## 🔧 **Current Project Configuration**

### **Project Details**
- **Project ID**: `smarthealthtracker-c002d`
- **Project Number**: `428367960312`
- **Package Name**: `com.smarthealthtracker`
- **Current API Key**: `AIzaSyBT6_hNngRtRNAbfrt5zYcQVwdb7VEIGtM`

### **Keystore Information**
- **Debug Keystore**: `~/.android/debug.keystore`
- **Release Keystore**: `smarthealthtracker-release-key.keystore`
- **Release Keystore Password**: `smarthealth123`
- **Release Key Alias**: `smarthealthtracker`
- **Release Key Password**: `smarthealth123`

## 🚀 **Testing Google Fit API**

### **Development Testing**
1. Use the debug SHA-1 key for development
2. Test on emulator or device with debug build
3. Verify Google Fit authentication works

### **Production Testing**
1. Use the release SHA-1 key for production
2. Build release APK with release keystore
3. Test on physical device with release build

## 📱 **Build Configuration**

### **Debug Build (Development)**
```bash
./gradlew assembleDebug
```

### **Release Build (Production)**
```bash
./gradlew assembleRelease
```

## 🔐 **Security Notes**

1. **Keep your keystore files secure** - never commit them to version control
2. **Use environment variables** for keystore passwords in production
3. **Backup your release keystore** - losing it means you can't update your app
4. **Use different keystores** for different environments (dev, staging, production)

## 🎯 **Expected Results**

After completing this setup:
- ✅ Google Fit authentication will work properly
- ✅ Users can sign in with Google accounts
- ✅ Health data can be read from Google Fit
- ✅ App can sync with Google Fit services
- ✅ Both debug and release builds will work

## 🆘 **Troubleshooting**

### **Common Issues**
1. **"Sign in failed"**: Check SHA-1 key matches exactly
2. **"API not enabled"**: Ensure Fitness API is enabled
3. **"Invalid package name"**: Verify package name matches exactly
4. **"OAuth consent screen"**: Complete OAuth consent screen setup

### **Verification Steps**
1. Check SHA-1 key in Google Cloud Console matches your keystore
2. Verify package name is exactly `com.smarthealthtracker`
3. Ensure all required APIs are enabled
4. Test with both debug and release builds

## 📞 **Support**

If you encounter any issues:
1. Check Google Cloud Console for error messages
2. Verify all SHA-1 keys are correctly entered
3. Ensure OAuth consent screen is properly configured
4. Test with a fresh Google account

---

**Your SmartHealthTracker app is now ready for Google Fit API integration!** 🎉
