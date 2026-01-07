# Smart Health Tracker - Architecture Documentation

## 🏗️ Overall Architecture

The Smart Health Tracker follows the **MVVM (Model-View-ViewModel)** architecture pattern with **Repository Pattern** for data management.

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   UI Layer      │    │  ViewModel      │    │  Data Layer     │
│   (Compose)     │◄──►│   Layer         │◄──►│   (Repository)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Screens       │    │  Business       │    │  Database       │
│   Components    │    │  Logic          │    │  Services       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📱 UI Layer (Jetpack Compose)

### Screen Components

#### 1. **AuthScreen.kt**
```kotlin
/**
 * PURPOSE: Handle user authentication (login/signup)
 * 
 * KEY COMPONENTS:
 * - Email/Password input fields
 * - Toggle between login/signup modes
 * - Form validation
 * - Firebase Auth integration
 * - Animated UI elements
 * 
 * STATE MANAGEMENT:
 * - Local state for form inputs
 * - ViewModel state for auth status
 * - Animation states for smooth transitions
 */
```

#### 2. **ProductionDashboardScreen.kt**
```kotlin
/**
 * PURPOSE: Main dashboard showing health overview
 * 
 * KEY COMPONENTS:
 * - Health score calculation and display
 * - Progress bars for each health metric
 * - Quick action cards for navigation
 * - Motivational messages
 * - Animated UI elements
 * 
 * DATA FLOW:
 * HealthViewModel -> Database -> UI Components
 */
```

#### 3. **WaterTrackingScreen.kt**
```kotlin
/**
 * PURPOSE: Track daily water intake
 * 
 * KEY COMPONENTS:
 * - Quick add buttons (250ml, 500ml, 750ml, 1L)
 * - Custom amount dialog
 * - Progress bar showing goal completion
 * - Sound feedback integration
 * - Today's total display
 * 
 * USER INTERACTION:
 * 1. User taps quick button or custom amount
 * 2. Sound plays (if enabled)
 * 3. Data updates in database
 * 4. UI refreshes with new totals
 */
```

#### 4. **StepTrackingScreen.kt**
```kotlin
/**
 * PURPOSE: Track daily step count
 * 
 * KEY COMPONENTS:
 * - Quick add buttons (1000, 2000, 5000, 10000 steps)
 * - Custom step entry dialog
 * - Progress visualization
 * - Sound feedback
 * - Statistics display
 * 
 * SIMILAR PATTERN TO WATER TRACKING
 */
```

#### 5. **SleepTrackingScreen.kt**
```kotlin
/**
 * PURPOSE: Track sleep duration and quality + meditation sounds
 * 
 * KEY COMPONENTS:
 * - Sleep logging dialog (start/end times, quality rating)
 * - Sleep duration calculation
 * - Meditation sounds section
 * - 4 meditation options (Rain, Ocean, Forest, White Noise)
 * - Timer display for meditation
 * - Sound management
 * 
 * UNIQUE FEATURES:
 * - Meditation sound integration
 * - Sleep quality rating system
 * - Automatic duration calculation
 */
```

## 🧠 ViewModel Layer

### 1. **AuthViewModel**
```kotlin
/**
 * RESPONSIBILITIES:
 * - Handle Firebase authentication
 * - Manage login/signup state
 * - Form validation
 * - Error handling
 * - User session management
 * 
 * STATE EXPOSED:
 * - user: FirebaseUser?
 * - isLoading: Boolean
 * - errorMessage: String?
 */
```

### 2. **HealthViewModel**
```kotlin
/**
 * RESPONSIBILITIES:
 * - Manage health data operations
 * - Calculate health scores
 * - Handle CRUD operations for health data
 * - Manage user goals
 * - Business logic for health tracking
 * 
 * STATE EXPOSED:
 * - currentHealthData: HealthData?
 * - userGoals: UserGoals?
 * - isLoading: Boolean
 * - errorMessage: String?
 */
```

### 3. **ThemeViewModel**
```kotlin
/**
 * RESPONSIBILITIES:
 * - Manage app theme settings
 * - Handle sound enable/disable
 * - User preferences
 * - Dark/light mode switching
 * 
 * STATE EXPOSED:
 * - soundEnabled: Boolean
 * - isDarkMode: Boolean
 */
```

## 💾 Data Layer

### Repository Pattern
```kotlin
/**
 * HealthRepository
 * 
 * PURPOSE: Abstract data access layer
 * 
 * RESPONSIBILITIES:
 * - Provide single source of truth for health data
 * - Handle data operations (CRUD)
 * - Manage data synchronization
 * - Abstract database implementation details
 * 
 * METHODS:
 * - getTodayHealthData(): Flow<HealthData?>
 * - addWaterIntake(amount: Float)
 * - addSteps(steps: Int)
 * - addSleepLog(start: String, end: String, duration: Float, quality: Int)
 * - updateUserGoals(goals: UserGoals)
 */
```

### Database Layer (Room)
```kotlin
/**
 * HealthDatabase
 * 
 * COMPONENTS:
 * - HealthDataDao: CRUD operations for health data
 * - UserGoalsDao: User goal management
 * - Database entities and relationships
 * 
 * ENTITIES:
 * - HealthData: Daily health metrics
 * - UserGoals: User-defined daily goals
 * - WaterLog, StepLog, SleepLog: Individual log entries
 */
```

## 🎵 Sound System Architecture

### SoundManager
```kotlin
/**
 * PURPOSE: Centralized sound management
 * 
 * COMPONENTS:
 * - SoundPool for efficient sound playback
 * - Sound file mapping and loading
 * - Play/pause controls
 * - Error handling for missing files
 * 
 * SOUND CATEGORIES:
 * 1. Interaction Sounds:
 *    - water_drop.mp3
 *    - step_sound.mp3
 *    - sleep_chime.mp3
 *    - success_chime.mp3
 *    - notification_sound.mp3
 * 
 * 2. Meditation Sounds:
 *    - rain_sounds.mp3
 *    - ocean_waves.mp3
 *    - forest_ambience.mp3
 *    - white_noise.mp3
 * 
 * USAGE PATTERN:
 * val soundManager = rememberSoundManager()
 * soundManager.playWaterSound()
 */
```

## 🔄 Data Flow Examples

### Water Intake Flow
```
1. User taps "250ml" button
   ↓
2. WaterTrackingScreen calls viewModel.addWaterIntake(250f)
   ↓
3. HealthViewModel calls repository.addWaterIntake(250f)
   ↓
4. Repository updates database with new water intake
   ↓
5. Database emits updated HealthData
   ↓
6. ViewModel receives updated data
   ↓
7. UI automatically updates with new totals
   ↓
8. SoundManager plays water drop sound
```

### Authentication Flow
```
1. User enters email/password and taps "Login"
   ↓
2. AuthScreen calls viewModel.signIn(email, password)
   ↓
3. AuthViewModel calls Firebase Auth
   ↓
4. Firebase returns authentication result
   ↓
5. ViewModel updates user state
   ↓
6. UI observes user state change
   ↓
7. Navigation triggers to main app
```

## 🎨 UI/UX Architecture

### Animation System
```kotlin
/**
 * ANIMATION TYPES:
 * 1. State-based animations (loading, success, error)
 * 2. Transition animations (screen changes)
 * 3. Micro-interactions (button presses, card scaling)
 * 4. Progress animations (progress bars, counters)
 * 
 * ANIMATION LIBRARIES:
 * - androidx.compose.animation
 * - androidx.compose.animation.core
 * 
 * COMMON PATTERNS:
 * - animateFloatAsState for smooth value changes
 * - AnimatedVisibility for show/hide transitions
 * - Crossfade for content switching
 */
```

### Theme System
```kotlin
/**
 * THEME COMPONENTS:
 * - Color palette for health metrics
 * - Typography scales
 * - Spacing system
 * - Component shapes and elevations
 * 
 * HEALTH COLORS:
 * - HealthBlue: Water tracking
 * - HealthGreen: Step tracking
 * - HealthPurple: Sleep tracking
 * - HealthRed: Error states
 * - HealthOrange: Warnings
 */
```

## 🔧 Dependency Injection (Hilt)

### Module Structure
```kotlin
/**
 * @Module
 * @InstallIn(SingletonComponent::class)
 * 
 * PROVIDES:
 * - Database instances
 * - Repository implementations
 * - ViewModel factories
 * - SoundManager instance
 * 
 * BENEFITS:
 * - Automatic dependency resolution
 * - Singleton management
 * - Easy testing with mock dependencies
 * - Clean separation of concerns
 */
```

## 📊 State Management

### Compose State
```kotlin
/**
 * STATE TYPES:
 * 1. Local State (remember/mutableStateOf):
 *    - Form inputs
 *    - UI visibility
 *    - Animation states
 * 
 * 2. ViewModel State (StateFlow/collectAsState):
 *    - Business data
 *    - Loading states
 *    - Error states
 * 
 * 3. Repository State (Flow):
 *    - Database data
 *    - Network data
 *    - Cached data
 */
```

## 🧪 Testing Strategy

### Unit Tests
- ViewModel logic testing
- Repository method testing
- Utility function testing
- SoundManager testing

### Integration Tests
- Database operations
- Authentication flow
- Navigation testing

### UI Tests
- Screen interaction testing
- Animation testing
- Accessibility testing

## 🚀 Performance Considerations

### Compose Optimizations
- LazyColumn for large lists
- remember for expensive calculations
- derivedStateOf for computed values
- key() for list item stability

### Database Optimizations
- Indexed queries
- Efficient data models
- Background thread operations
- Connection pooling

### Sound Optimizations
- SoundPool for efficient playback
- Preloading common sounds
- Resource cleanup
- Memory management

## 🔒 Security Considerations

### Authentication
- Firebase Auth for secure authentication
- Token-based session management
- Secure password handling

### Data Protection
- Local database encryption
- Secure data transmission
- User privacy protection

## 📱 Platform Integration

### Android Features
- Material 3 design system
- Adaptive layouts for different screen sizes
- Accessibility support
- Background processing

### Future Integrations
- Google Fit API for automatic step tracking
- Wearable device support
- Health sensor integration
- Cloud synchronization

---

This architecture provides a solid foundation for the Smart Health Tracker app, ensuring maintainability, testability, and scalability for future enhancements.
