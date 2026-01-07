# Smart Health Tracker 📱

A comprehensive Android health tracking application built with **Jetpack Compose** and **Kotlin**. Track your water intake, steps, sleep, and enjoy meditation sounds for better wellness.

## 🌟 Features

### 🔐 Authentication
- **Firebase Authentication** integration
- Modern animated login/signup UI
- Form validation and error handling
- Password visibility toggles
- Smooth animations for better UX

### 📊 Dashboard
- **Health Score Calculation** - Overall wellness score based on daily goals
- **Progress Overview** - Visual progress bars for water, steps, and sleep
- **Quick Actions** - Direct access to tracking screens
- **Motivational Messages** - Encouraging feedback based on progress
- **Animated UI** - Smooth transitions and micro-interactions

### 💧 Water Tracking
- **Quick Add Buttons** - 250ml, 500ml, 750ml, 1L options
- **Custom Amount** - Add any amount via dialog
- **Daily Goal Tracking** - Visual progress towards daily water goal
- **Sound Effects** - Water drop sound when logging intake
- **History View** - See past water intake records

### 🚶 Step Tracking
- **Quick Add Buttons** - 1000, 2000, 5000, 10000 steps
- **Custom Step Entry** - Add any number of steps
- **Goal Progress** - Track progress towards daily step goal
- **Sound Feedback** - Step sound when logging activity
- **Statistics** - View step count and goal achievement

### 😴 Sleep Tracking
- **Sleep Logging** - Record sleep start/end times and quality
- **Sleep Duration Calculation** - Automatic calculation of sleep hours
- **Quality Rating** - Rate sleep quality from 1-5 stars
- **Sleep Tips** - Helpful tips for better sleep
- **Sound Effects** - Sleep chime when logging sleep

### 🧘‍♀️ Meditation & Sleep Sounds
- **4 Sound Options**:
  - 🌧️ **Rain** - Gentle rain sounds for relaxation
  - 🌊 **Ocean** - Ocean wave sounds for calm
  - 🌲 **Forest** - Forest ambience for nature sounds
  - 📻 **White Noise** - Calming white noise for focus
- **Play/Pause Controls** - Easy sound management
- **Timer Display** - Shows how long you've been listening
- **Visual Feedback** - Cards highlight when selected/playing

### 🔊 Sound System
- **Sound Effects** for all user interactions
- **Meditation Sounds** for relaxation and sleep
- **Sound Manager** - Centralized sound management
- **Enable/Disable** - User can turn sounds on/off
- **Graceful Fallback** - Works even without sound files

## 🏗️ Architecture

### MVVM Pattern
```
UI Layer (Compose) ↔ ViewModel ↔ Repository ↔ Database
```

### Key Components

#### 📱 UI Layer (Jetpack Compose)
- **AuthScreen** - Login/signup with animations
- **ProductionDashboardScreen** - Main dashboard with health overview
- **WaterTrackingScreen** - Water intake tracking
- **StepTrackingScreen** - Step counting and logging
- **SleepTrackingScreen** - Sleep tracking + meditation sounds
- **ReportsScreen** - Data visualization and charts

#### 🧠 ViewModels
- **AuthViewModel** - Handles authentication logic
- **HealthViewModel** - Manages health data and business logic
- **ThemeViewModel** - Manages app theme and settings

#### 💾 Data Layer
- **Room Database** - Local SQLite database for health data
- **Repository Pattern** - Abstracts data access
- **Firebase Auth** - User authentication
- **Data Models** - HealthData, WaterLog, StepLog, SleepLog, UserGoals

#### 🎵 Sound System
- **SoundManager** - Centralized sound management
- **SoundPool** - Efficient sound playback
- **Raw Resources** - Sound files in res/raw/

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Repository Pattern
- **Database**: Room (SQLite)
- **Authentication**: Firebase Auth
- **Dependency Injection**: Hilt
- **Navigation**: Navigation Compose
- **Animations**: Compose Animations
- **Sound**: SoundPool
- **Build System**: Gradle

## 📁 Project Structure

```
app/src/main/java/com/smarthealthtracker/
├── data/
│   ├── database/          # Room database setup
│   ├── model/            # Data models
│   ├── repository/       # Repository implementations
│   └── service/          # External services (Google Fit, etc.)
├── ui/
│   ├── screen/           # Compose screens
│   ├── theme/            # App theming
│   ├── utils/            # Utility classes (SoundManager, etc.)
│   └── viewmodel/        # ViewModels
└── MainActivity.kt       # Main activity
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+
- Kotlin 1.8+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Add your Firebase configuration to `google-services.json`
4. Build and run the project

### Adding Sound Files
1. Place sound files in `app/src/main/res/raw/`:
   - `water_drop.mp3` - Water intake sound
   - `step_sound.mp3` - Step logging sound
   - `sleep_chime.mp3` - Sleep logging sound
   - `success_chime.mp3` - Success/achievement sound
   - `notification_sound.mp3` - General notification sound
   - `rain_sounds.mp3` - Rain meditation sound
   - `ocean_waves.mp3` - Ocean meditation sound
   - `forest_ambience.mp3` - Forest meditation sound
   - `white_noise.mp3` - White noise meditation sound

2. The app will automatically use these sounds when available

## 📊 Data Models

### HealthData
```kotlin
data class HealthData(
    val id: Long = 0,
    val date: String,
    val waterIntake: Float = 0f,
    val steps: Int = 0,
    val sleepHours: Float = 0f,
    val sleepQuality: Int = 0
)
```

### UserGoals
```kotlin
data class UserGoals(
    val id: Long = 0,
    val dailyWaterGoal: Float = 2.5f,  // liters
    val dailyStepGoal: Int = 10000,    // steps
    val dailySleepGoal: Float = 8f     // hours
)
```

## 🎨 UI/UX Features

### Animations
- **Logo scaling** on loading states
- **Form transitions** between login/signup
- **Card scaling** on press interactions
- **Fade in/out** for smooth screen transitions
- **Slide animations** for list items

### Theme
- **Material 3** design system
- **Custom color palette** for health metrics
- **Dark/Light mode** support
- **Consistent spacing** and typography

### Accessibility
- **Content descriptions** for screen readers
- **High contrast** color schemes
- **Touch target sizing** for easy interaction
- **Keyboard navigation** support

## 🔧 Configuration

### Firebase Setup
1. Create a Firebase project
2. Add Android app to Firebase
3. Download `google-services.json`
4. Place in `app/` directory
5. Enable Authentication in Firebase Console

### Database Configuration
- Database name: `health_database`
- Version: 1
- Auto-migration enabled
- Backup and restore support

## 📱 Screenshots

### Authentication Screen
- Modern gradient background
- Animated logo and form
- Toggle between login/signup
- Form validation feedback

### Dashboard
- Health score display
- Progress bars for each metric
- Quick action cards
- Motivational messages

### Tracking Screens
- Intuitive input methods
- Visual progress indicators
- Sound feedback
- History and statistics

### Meditation Section
- Beautiful sound selection cards
- Timer display
- Play/pause controls
- Calming color scheme

## 🚀 Future Enhancements

- [ ] Google Fit integration for automatic step tracking
- [ ] Sleep tracking with device sensors
- [ ] Health insights and recommendations
- [ ] Social features and challenges
- [ ] Wearable device support
- [ ] Cloud sync and backup
- [ ] Advanced analytics and reports
- [ ] Custom meditation sound uploads

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Material Design 3 for UI components
- Jetpack Compose for modern Android UI
- Firebase for authentication
- Room for local database
- SoundPool for audio management

---

**Built with ❤️ for better health and wellness**