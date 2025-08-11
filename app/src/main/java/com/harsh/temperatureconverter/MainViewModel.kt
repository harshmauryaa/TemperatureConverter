package com.harsh.temperatureconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class MainViewModel : ViewModel() {
    
    private val _celsius = MutableLiveData("")
    val celsius: MutableLiveData<String> = _celsius
    
    private val _fahrenheitFormatted = MediatorLiveData<String>()
    val fahrenheitFormatted: LiveData<String> = _fahrenheitFormatted
    
    init {
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