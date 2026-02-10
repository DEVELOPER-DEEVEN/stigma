# STIGMA

> **Decision-Advocacy Intelligence Platform**
>
> A premium, AI-powered decision analysis tool featuring a glassmorphic UI and offline-first architecture.

## 📋 Overview

Stigma leverages Azure OpenAI to provide decision advocacy and analysis. It is built with a focus on:
- **Hygiene:** Clean Architecture (MVVM + Repository Pattern).
- **Performance:** Offline-first with Room and reactive data streams.
- **UX:** High-fidelity glassmorphic design system using Jetpack Compose.

## 🛠 Development

### Prerequisites
- **JDK:** 17
- **Android Studio:** Hedgehog (2023.1.1) or newer
- **Android SDK:** API 34 (UpsideDownCake)

### Configuration
Create a `local.properties` file in the project root to configure Azure OpenAI credentials:

```properties
sdk.dir=/path/to/android/sdk
AZURE_OPENAI_ENDPOINT=https://your-resource.openai.azure.com/
AZURE_OPENAI_KEY=your-api-key
AZURE_OPENAI_DEPLOYMENT=gpt-4
AZURE_OPENAI_API_VERSION=2024-02-01
```

### Build
Run the debug build via Gradle:

```bash
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

## 🏗 Architecture

The project follows [Google's Guide to App Architecture](https://developer.android.com/topic/architecture):

- **UI Layer**: Jetpack Compose, Material 3
- **Domain Layer**: Use Cases, Repository Interfaces (Pure Kotlin)
- **Data Layer**: Repositories (Impl), Data Sources (Room, Retrofit)
- **DI**: Hilt

## 📦 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| DI | Dagger Hilt |
| Database | Room (SQLite) |
| Network | Retrofit, OkHttp |
| AI | Azure OpenAI SDK |
| Async | Coroutines, Flow |
| Logging | Timber |

## ⚖️ License

Private Repository. All rights reserved.
