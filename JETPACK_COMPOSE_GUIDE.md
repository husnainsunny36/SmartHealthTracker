# Jetpack Compose - Simple Guide

## What is Jetpack Compose? 🤔

Jetpack Compose is Google's modern toolkit for building **native Android UIs**. Think of it as a new way to create the visual parts of your Android app - buttons, text, images, screens, etc.

### Traditional Android UI vs Jetpack Compose

**Old Way (XML + Java/Kotlin):**
```xml
<!-- activity_main.xml -->
<LinearLayout>
    <TextView android:text="Hello World" />
    <Button android:text="Click Me" />
</LinearLayout>
```
```kotlin
// MainActivity.kt
findViewById<Button>(R.id.button).setOnClickListener {
    // Handle click
}
```

**New Way (Jetpack Compose):**
```kotlin
@Composable
fun MainScreen() {
    Column {
        Text("Hello World")
        Button(onClick = { /* Handle click */ }) {
            Text("Click Me")
        }
    }
}
```

## Key Concepts 🧠

### 1. **Composable Functions**
- Functions that describe UI components
- Marked with `@Composable` annotation
- Can be called from other composable functions

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

### 2. **State Management**
- UI automatically updates when data changes
- Use `remember` to store data that survives recomposition
- Use `mutableStateOf` for data that can change

```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

### 3. **Recomposition**
- When state changes, Compose automatically redraws affected UI parts
- Only recomposes what actually changed (smart optimization)
- Fast and efficient

## Basic UI Components 🎨

### Text
```kotlin
Text(
    text = "Hello World",
    fontSize = 18.sp,
    color = Color.Blue,
    fontWeight = FontWeight.Bold
)
```

### Button
```kotlin
Button(
    onClick = { /* Action */ },
    colors = ButtonDefaults.buttonColors(
        containerColor = Color.Blue
    )
) {
    Text("Click Me")
}
```

### TextField (Input)
```kotlin
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Enter your name") }
)
```

### Image
```kotlin
Image(
    painter = painterResource(R.drawable.my_image),
    contentDescription = "My Image",
    modifier = Modifier.size(100.dp)
)
```

## Layout Components 📐

### Column (Vertical Stack)
```kotlin
Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text("First item")
    Text("Second item")
    Text("Third item")
}
```

### Row (Horizontal Stack)
```kotlin
Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()
) {
    Text("Left")
    Text("Right")
}
```

### Box (Overlay)
```kotlin
Box {
    Image(/* background image */)
    Text(
        text = "Overlay text",
        modifier = Modifier.align(Alignment.Center)
    )
}
```

## Modifiers - Styling & Behavior 🎯

Modifiers are like CSS properties - they change how components look and behave:

```kotlin
Text(
    text = "Styled Text",
    modifier = Modifier
        .padding(16.dp)           // Add space around
        .background(Color.Gray)   // Background color
        .border(2.dp, Color.Blue) // Border
        .padding(8.dp)            // Inner padding
        .clickable { /* Click action */ }
)
```

### Common Modifiers:
- `padding()` - Add space around component
- `fillMaxWidth()` - Take full width
- `fillMaxHeight()` - Take full height
- `size(width, height)` - Set specific size
- `background()` - Set background color
- `border()` - Add border
- `clickable()` - Make clickable

## Navigation 🧭

### Setup Navigation
```kotlin
@Composable
fun MyApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
```

### Navigation Actions
```kotlin
// Navigate to screen
navController.navigate("screen_name")

// Go back
navController.popBackStack()

// Navigate with data
navController.navigate("profile/user123")
```

## State Management Patterns 📊

### 1. **Local State** (Simple)
```kotlin
@Composable
fun LocalStateExample() {
    var isVisible by remember { mutableStateOf(true) }
    
    if (isVisible) {
        Text("I'm visible!")
    }
    
    Button(onClick = { isVisible = !isVisible }) {
        Text("Toggle")
    }
}
```

### 2. **ViewModel State** (Complex)
```kotlin
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun updateData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }
}

@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        Text("Data loaded!")
    }
}
```

## Lists and Data Display 📋

### LazyColumn (Scrollable List)
```kotlin
@Composable
fun UserList(users: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserCard(user = user)
        }
    }
}
```

### LazyRow (Horizontal Scrollable List)
```kotlin
LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(items) { item ->
        ItemCard(item = item)
    }
}
```

## Material Design Components 🎨

### Card
```kotlin
Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Card Title", style = MaterialTheme.typography.headlineSmall)
        Text("Card content goes here")
    }
}
```

### FloatingActionButton
```kotlin
FloatingActionButton(
    onClick = { /* Action */ },
    containerColor = MaterialTheme.colorScheme.primary
) {
    Icon(Icons.Default.Add, contentDescription = "Add")
}
```

### BottomNavigation
```kotlin
NavigationBar {
    items.forEach { screen ->
        NavigationBarItem(
            icon = { Icon(screen.icon, contentDescription = screen.title) },
            label = { Text(screen.title) },
            selected = currentRoute == screen.route,
            onClick = { onNavigate(screen.route) }
        )
    }
}
```

## Theming & Colors 🎨

### Custom Theme
```kotlin
@Composable
fun MyApp() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            surface = Color(0xFFFFFBFE)
        ),
        typography = Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
    ) {
        // Your app content
    }
}
```

## Performance Tips ⚡

### 1. **Use `remember` for expensive calculations**
```kotlin
@Composable
fun ExpensiveComponent(data: List<Data>) {
    val processedData = remember(data) {
        data.map { /* expensive processing */ }
    }
    // Use processedData
}
```

### 2. **Use `LazyColumn` for large lists**
```kotlin
// Good - Only renders visible items
LazyColumn {
    items(largeList) { item ->
        ItemComponent(item)
    }
}

// Bad - Renders all items at once
Column {
    largeList.forEach { item ->
        ItemComponent(item)
    }
}
```

### 3. **Avoid unnecessary recomposition**
```kotlin
@Composable
fun OptimizedComponent() {
    val stableData = remember { /* stable data */ }
    
    // This won't cause recomposition if stableData doesn't change
    Text(stableData.toString())
}
```

## Common Patterns 🔄

### 1. **Loading States**
```kotlin
@Composable
fun DataScreen() {
    when (val state = dataState) {
        is Loading -> CircularProgressIndicator()
        is Success -> DataContent(state.data)
        is Error -> ErrorMessage(state.message)
    }
}
```

### 2. **Form Handling**
```kotlin
@Composable
fun ContactForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    
    LaunchedEffect(name, email) {
        isValid = name.isNotEmpty() && email.contains("@")
    }
    
    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Button(
            onClick = { /* Submit */ },
            enabled = isValid
        ) {
            Text("Submit")
        }
    }
}
```

## Why Use Jetpack Compose? 🚀

### Advantages:
- **Less Code**: 50% less code compared to XML
- **Declarative**: Describe what UI should look like, not how to build it
- **Reactive**: UI automatically updates when data changes
- **Type-Safe**: Compile-time safety, fewer runtime crashes
- **Powerful**: Built-in animations, theming, and Material Design
- **Modern**: Uses Kotlin's latest features

### When to Use:
- ✅ New Android projects
- ✅ Modernizing existing apps
- ✅ Complex, dynamic UIs
- ✅ Apps with frequent UI updates

### When NOT to Use:
- ❌ Very simple, static UIs
- ❌ Legacy projects with tight deadlines
- ❌ Teams not familiar with Kotlin

## Getting Started 🏁

### 1. **Add Dependencies**
```kotlin
// build.gradle.kts
dependencies {
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material3:material3:$compose_version")
    implementation("androidx.activity:activity-compose:$activity_compose_version")
}
```

### 2. **Create Your First Composable**
```kotlin
@Composable
fun MyFirstScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Compose!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Action */ }) {
            Text("Get Started")
        }
    }
}
```

### 3. **Set Up Your Activity**
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                MyFirstScreen()
            }
        }
    }
}
```


**Remember**: Jetpack Compose is about thinking declaratively - describe what your UI should look like based on your app's state, and let Compose handle the rest! 🎯
