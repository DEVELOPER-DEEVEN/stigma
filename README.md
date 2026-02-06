# STIGMA - Decision-Advocacy Intelligence

Premium AI-powered decision analysis platform with ultra-artistic UI.

## 🎨 Features

- **Premium Glassmorphic UI** - Holographic effects, neon glows, particle backgrounds
- **AI-Powered Analysis** - Azure OpenAI integration for decision advocacy
- **Offline-First** - Room database with reactive data streams
- **Real-Time Updates** - Firebase Firestore integration ready
- **Production Architecture** - MVVM + Hilt DI + Repository pattern

## 🚀 Building the APK

### Option 1: GitHub Actions (Recommended - No local setup needed!)

1. **Push to GitHub**:
   ```bash
   git init
   git add .
   git commit -m "Initial commit - Premium STIGMA app"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/stigma.git
   git push -u origin main
   ```

2. **Set up secrets** (in GitHub repository settings):
   - Go to: Settings → Secrets and variables → Actions → New repository secret
   - Add these secrets:
     - `AZURE_OPENAI_ENDPOINT`
     - `AZURE_OPENAI_KEY`
     - `AZURE_OPENAI_DEPLOYMENT`
     - `AZURE_OPENAI_API_VERSION`

3. **Download APK**:
   - Go to: Actions tab → Latest workflow run
   - Download `stigma-debug-apk` artifact
   - Extract ZIP to get `app-debug.apk`

### Option 2: Local Build with Android Studio

1. Install [Android Studio](https://developer.android.com/studio)
2. Open this project
3. Build → Build Bundle(s) / APK(s) → Build APK(s)
4. APK location: `app/build/outputs/apk/debug/app-debug.apk`

## 📱 Installation

1. Enable "Install from Unknown Sources" on your Android device
2. Transfer the APK to your device
3. Install and enjoy!

## 🎯 Architecture

- **UI Layer**: Jetpack Compose with premium components
- **Domain Layer**: Clean architecture with use cases
- **Data Layer**: Room (local) + Firebase (remote sync)
- **DI**: Hilt for dependency injection
- **Reactive**: Kotlin Coroutines + Flow

## 🌟 Premium UI Components

- `GlassCard` - Glassmorphic cards with blur
- `NeonButton` - Pulsing glow buttons
- `ParticleBackground` - Animated particles
- `AnimatedWave` - Sine wave animations
- `ShimmerEffect` - Loading states
- `PulsingIndicators` - Live status indicators

## 📦 Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Hilt (DI)
- Room (Database)
- Firebase (Firestore, Analytics, Crashlytics)
- Retrofit + OkHttp
- Azure OpenAI
- Lottie Animations
- Google Fonts

## 📄 License

Private project - All rights reserved

---

**Built with ❤️ and cutting-edge Android tech**
