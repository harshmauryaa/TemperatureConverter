#Temperature Converter (Android)

A sleek Android app that instantly converts **Celsius** to **Fahrenheit** with a modern dark-themed UI.

##Features
- **Real-time conversion** – Updates Fahrenheit value as you type
- **Clean, dark UI** – Minimal and elegant Material Design
- **MVVM architecture** – Clear separation of UI & logic
- **Data Binding** – Automatic, lifecycle-aware UI updates
- **Error-safe input** – Handles empty or invalid values gracefully

##UI Snapshot
Centered Fahrenheit display above a rounded Celsius input field on a black background with white text and subtle gray accents.

##Tech Highlights
- **ViewModel + LiveData** for reactive updates  
- **Material Components** for smooth input fields  
- **Two-way Data Binding** for instant conversions  
- Conversion formula: `F = (C × 9/5) + 32` (formatted to 2 decimals)

##Example
- 0°C → 32.00°F  
- 37.5°C → 99.50°F  
- -40°C → -40.00°F  
