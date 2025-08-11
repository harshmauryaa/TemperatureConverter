# Temperature Converter Android App

A native Android temperature converter app demonstrating MVVM architecture with Data Binding.

## Data Binding Implementation

### 1. Build Configuration
```kotlin
// app/build.gradle.kts
android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
}
```

### 2. Layout with Data Binding
```xml
<!-- app/src/main/res/layout/activity_main.xml -->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="viewModel"
            type="com.harsh.temperatureconverter.TemperatureViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/black">

        <TextView
            android:id="@+id/fahrenheit_output"
            android:text="@{viewModel.fahrenheitFormatted}"
            android:textColor="@color/text_primary"
            android:textSize="96sp"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent" />

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/celsius_input_layout"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            app:suffixText="°C"
            app:layout_constraintTop_toBottomOf="@+id/fahrenheit_output"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/celsius_input"
                android:layout_width="match_parent"
                android:layout_height="64dp"
                android:text="@={viewModel.celsius}"
                android:hint="Enter Celsius"
                android:inputType="numberDecimal" />

        </com.google.android.material.textfield.TextInputLayout>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### 3. ViewModel with LiveData
```kotlin
// app/src/main/java/com/harsh/temperatureconverter/MainViewModel.kt
class MainViewModel : ViewModel() {
    
    private val _celsius = MutableLiveData<String>("")
    val celsius: MutableLiveData<String> = _celsius
    
    private val _fahrenheitFormatted = MediatorLiveData<String>()
    val fahrenheitFormatted: LiveData<String> = _fahrenheitFormatted
    
    init {
        // Observe celsius changes and update fahrenheit accordingly
        _fahrenheitFormatted.addSource(_celsius) { celsiusValue ->
            _fahrenheitFormatted.value = convertCelsiusToFahrenheit(celsiusValue)
        }
    }
    
    private fun convertCelsiusToFahrenheit(celsiusString: String): String {
        if (celsiusString.isBlank()) {
            return ""
        }
        
        return try {
            val celsiusValue = celsiusString.toDoubleOrNull()
            if (celsiusValue == null) {
                ""
            } else {
                val fahrenheitValue = (celsiusValue * 9.0 / 5.0) + 32.0
                String.format(Locale.US, "%.2f°", fahrenheitValue)
            }
        } catch (e: NumberFormatException) {
            ""
        }
    }
}
```

### 4. Activity with Data Binding Setup
```kotlin
// app/src/main/java/com/harsh/temperatureconverter/MainActivity.kt
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TemperatureViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Data Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        // Set up Data Binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
```


## Key Data Binding Features Used

1. **Two-way Binding**: `android:text="@={viewModel.celsius}"` - Updates both UI and ViewModel
2. **One-way Binding**: `android:text="@{viewModel.fahrenheitFormatted}"` - Updates UI from ViewModel
3. **Variable Declaration**: `<variable name="viewModel" type="...">` - Links ViewModel to layout
4. **Lifecycle Owner**: `binding.lifecycleOwner = this` - Prevents memory leaks
5. **LiveData Observation**: Automatic UI updates when LiveData changes

## Conversion Logic

- **Formula**: F = (C × 9/5) + 32
- **Formatting**: 2 decimal places with degree symbol
- **Edge Cases**: Empty input → empty output, invalid numbers → empty output
- **Error Handling**: Safe parsing prevents crashes

## Project Structure
```
app/src/main/
├── java/com/harsh/temperatureconverter/
│   ├── MainActivity.kt          # Data Binding setup
│   └── MainViewModel.kt  # MVVM ViewModel
└── res/
    ├── layout/activity_main.xml # Data Binding layout
```
