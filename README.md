# ✦ ASTRA
### Adaptive Sales & Transactional Relationship Assistant

> AI-powered sales intelligence for enterprise teams that need to remember context, learn from outcomes, and act with better judgment.

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin" alt="Kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose" alt="Compose" />
  <img src="https://img.shields.io/badge/Memory-Hindsight-6366F1?style=for-the-badge" alt="Hindsight" />
  <img src="https://img.shields.io/badge/Status-MVP-success?style=for-the-badge" alt="Status" />
</p>

---

## Overview

ASTRA is a memory-first Android application designed for sales teams working in complex enterprise deals. Instead of treating customer interactions as isolated events, ASTRA stores them as a connected timeline of stakeholders, objections, recommendations, and outcomes.

The app helps reps understand:

- what happened in prior conversations
- which objections were raised and how they were handled
- what actions worked or failed
- what should be discussed next
- how the deal has evolved over time

ASTRA turns historical sales context into actionable intelligence.

---

## The problem

In enterprise sales, context gets lost across:

- meetings
- notes
- call summaries
- pricing conversations
- stakeholder updates
- objections and follow-ups
- prior outcomes

The result is that teams often repeat mistakes, miss patterns, and struggle to connect deal history to future decisions.

A salesperson may remember the customer had a concern before, but not:

- what was tried
- whether it worked
- what happened next
- which strategy is most likely to succeed now

---

## The solution

ASTRA is built around a simple idea: sales intelligence should remember and learn.

It captures memory across the deal lifecycle and helps teams reason over it with AI-powered support.

### Core loop

- Retain customer interaction memory
- Recall relevant history
- Reflect on cause and effect
- Recommend the next best action
- Learn from the outcome

This creates a continuous memory loop that improves decision quality over time.

---

## Why ASTRA is different

### 1. Memory-to-outcome learning
ASTRA does not treat objections as isolated problems. It connects:

- what was said
- what action was taken
- how the customer responded
- whether the approach worked
- what should happen next time

### 2. Deal timeline intelligence
ASTRA surfaces a deal's progression over time so teams can understand how priorities, objections, and strategy evolved.

### 3. Evidence-backed guidance
Recommendations are tied back to memory and historical context, making them more explainable and trustworthy.

---

## Features

- Deal overview and lifecycle tracking
- Stakeholder and relationship insights
- Memory timeline for historical context
- AI-powered ask interface for deal guidance
- Meeting brief generation
- Follow-up suggestion workflows
- Evidence-backed recommendations
- Demo mode for product storytelling
- Android app experience built with Jetpack Compose

---

## Architecture

```text
┌──────────────────────────────────────────────────────────────┐
│                     ASTRA Android App                       │
│  Landing → Onboarding → Dashboard → Deals → Deal Detail     │
│  Memory → Insights → Time Machine → Ask ASTRA → Follow-up   │
└───────────────────────────────┬──────────────────────────────┘
                                │
                                ▼
┌──────────────────────────────────────────────────────────────┐
│                 Presentation Layer (Compose UI)              │
│  Screens, Navigation, ViewModel, State, Adaptive Layout     │
└───────────────────────────────┬──────────────────────────────┘
                                │
                                ▼
┌──────────────────────────────────────────────────────────────┐
│                   Repository / Data Layer                    │
│  AstraRepositoryImpl → Retrofit API → Hindsight endpoints   │
│  local memory, preferences, and deal context management     │
└───────────────────────────────┬──────────────────────────────┘
                                │
                                ▼
┌──────────────────────────────────────────────────────────────┐
│                       AI + Memory Layer                      │
│  Hindsight Memory Retrieval → Deal Summary → Recommendation │
│  Follow-up generation → Timeline reasoning → Evidence       │
└───────────────────────────────┬──────────────────────────────┘
                                │
                                ▼
┌──────────────────────────────────────────────────────────────┐
│                     External Services                         │
│  Hindsight API, AI inference, structured sales intelligence │
└──────────────────────────────────────────────────────────────┘
```

### App flow

```text
User opens ASTRA
      ↓
Landing screen
      ↓
Onboarding / authentication
      ↓
Dashboard overview
      ↓
Select deal / stakeholder / account
      ↓
Review memory timeline and insights
      ↓
Ask ASTRA for next action
      ↓
System retrieves relevant historical context
      ↓
AI recommends action with evidence
      ↓
User reviews recommendation and follows up
      ↓
Outcome is saved back into deal memory
```

---

## Tech stack

### Mobile app
- Kotlin
- Android SDK
- Jetpack Compose
- Material 3
- AndroidX lifecycle, navigation, activity, and adaptive layouts
- CameraX and location services
- DataStore and Room for local persistence

### Networking & API
- Retrofit
- OkHttp
- Moshi
- Kotlin Serialization
- Coroutines for async processing

### AI / memory integration
- Hindsight API integration for memory and insight retrieval
- Secure configuration via `local.properties`
- BuildConfig-based environment variables

### Development tooling
- Gradle Kotlin DSL
- Android Studio
- KSP for code generation
- JUnit for testing

---

## Project structure

```text
ASTRA/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/astra/
│   │   │   │   ├── data/
│   │   │   │   ├── ui/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ...
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── gradle.properties
├── local.properties
├── .gitignore
└── README.md
```

---

## Setup on your own PC

### Prerequisites

- Android Studio
- JDK 17+
- Android SDK installed and configured
- Git installed
- A GitHub account (optional, if you want to clone or push changes)

### 1. Clone the repository

```bash
git clone https://github.com/Geethikadasari7/Astra.git
cd Astra
```

### 2. Install Android Studio

Download and install Android Studio from:

https://developer.android.com/studio

Then open the project in Android Studio and let Gradle sync.

### 3. Configure Android SDK

If Android Studio prompts you to install the SDK, accept the setup. Make sure the following are installed:

- Android SDK Platform
- Android SDK Build-Tools
- Android Emulator (optional)
- Platform Tools

### 4. Add your local environment variables

Create or edit `local.properties` in the project root:

```properties
sdk.dir=C\:\\Users\\<YourUserName>\\AppData\\Local\\Android\\Sdk
HINDSIGHT_API_KEY=your_api_key_here
HINDSIGHT_BASE_URL=https://api.hindsight.vectorize.io/
```

> This file is local to your machine. Do not commit your real API key to GitHub.

### 5. Build the app

```bash
./gradlew assembleDebug
```

### 6. Run tests

```bash
./gradlew test
```

### 7. Launch on emulator or device

Open Android Studio, choose an emulator or connected device, and click Run.

---

## Download ASTRA app

You can run the app directly from source using Android Studio, or build an APK for installation on a device.

### Build APK

```bash
./gradlew assembleDebug
```

The generated APK will usually be in:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Install APK on Android device

1. Enable "Install unknown apps" or "Allow APK installs" on your phone.
2. Transfer the APK to the device.
3. Tap the file to install.
4. Open the app once installation completes.

### Optional: install from a release build

If a signed release APK or AAB is later published, you can distribute it using GitHub Releases or a private distribution channel.

---

## Example user flow

```text
Landing screen
  ↓
Onboarding
  ↓
Dashboard
  ↓
Select deal
  ↓
View deal timeline and memory
  ↓
Ask ASTRA about next action
  ↓
Review evidence and recommendation
  ↓
Take action and update outcomes
```

---

## Demo story

ASTRA is positioned around an enterprise sales scenario where a deal evolves over time, stakeholders change priority, objections appear, and recommendations improve as the relationship deepens.

The app demonstrates:

- memory retention across interactions
- timeline-based understanding
- AI-guided follow-up suggestions
- evidence-backed sales recommendations
- improved understanding of customer concerns and outcomes

---

## Built by

### Geethika Dasari
AI & Software Developer

**AI • Agentic Systems • Android Development • Product Thinking**

---

## Final message

> ASTRA helps sales teams move from scattered memory to informed action.
>
> It remembers what happened, explains why it matters, and helps the next step feel smarter.

---

## License

This project is currently for personal and portfolio use unless otherwise specified.

