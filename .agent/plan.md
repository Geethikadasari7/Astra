# Project Plan

ASTRA - Adaptive Sales Intelligence & Relationship Agent. Build a complete, polished, production-quality Android application using Jetpack Compose that embodies ASTRA: an adaptive sales intelligence agent whose intelligence improves through persistent memory and experience using Hindsight by Vectorize. Features include: Acme Financial hero demo, Memory Impact comparison (Generic vs Astra), interactive Memory Timeline, Why? button supporting evidence, Learning Curve visualization, Add Interaction form with Hindsight retain, AI Meeting Brief with recall & reflection, Follow-up generator, Demo mode, and premium enterprise UI.

## Project Brief

# Astra Project Brief (MVP)

## Features

1. **Acme Financial Hero Demo & Demo Mode**
   - Interactive showcase demonstrating Astra's adaptive sales intelligence compared to generic AI tools.
   - Quick-start demo mode with pre-populated enterprise data and sales scenarios.

2. **AI Meeting Brief with Recall & Reflection**
   - Context-aware meeting preparation summarizing previous client touchpoints, relationship health, and key talking points.
   - Built-in recall and reflection capabilities powered by persistent memory.

3. **Interactive Memory Timeline & "Why?" Evidence**
   - Chronological visualization of past interactions and memory impact.
   - Transparent "Why?" buttons providing clear supporting evidence for recommendations and insights.

4. **Add Interaction Form with Hindsight Retain**
   - Streamlined logging form for capturing client interactions, notes, and action items.
   - Direct integration with Hindsight by Vectorize to retain memory and continuously improve agent intelligence.

5. **AI Follow-Up Generator**
   - Automated, personalized follow-up message generation tailored to meeting notes and historical relationship context.

## High-Level Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Navigation & Adaptive Strategy:** 
  - **Jetpack Navigation 3** (state-driven navigation)
  - **Compose Material Adaptive Library** (for multi-pane layouts, window size classes, and adaptive navigation rails/bars)
- **Concurrency & State Management:** Kotlin Coroutines & Flow, Jetpack ViewModel
- **Persistence & Intelligence Layer:** Hindsight by Vectorize API integration (for persistent memory and adaptive sales intelligence)
- **Networking:** Ktor / Retrofit & kotlinx.serialization

## Implementation Steps

### Task_1_CoreArchitectureAndHindsightApi: Set up core architecture, data models, Hindsight by Vectorize API client with API_KEY integration, and repository layer.
- **Status:** COMPLETED
- **Updates:** Successfully set up core architecture, data models, Hindsight API client, and AstraRepository with Acme Financial demo data and live Hindsight integration. Verified build and unit tests.
- **Acceptance Criteria:**
  - API_KEY integration is fully configured
  - Data models and networking client implemented successfully
  - Project builds successfully

### Task_2_AcmeDemoAndMeetingBrief: Implement Acme Financial Hero Demo & Demo Mode and AI Meeting Brief with Recall & Reflection screens using Jetpack Compose and Material 3.
- **Status:** COMPLETED
- **Updates:** Successfully implemented Acme Financial Hero Demo, AI Meeting Brief with Recall & Reflection, Why? evidence dialogs, and Hackathon Demo Mode controls using Jetpack Compose and Material 3. Verified build and tests.
- **Acceptance Criteria:**
  - Acme Financial demo showcase and demo mode working
  - AI Meeting Brief screen with recall and reflection functional
  - UI builds successfully without errors

### Task_3_MemoryTimelineAndInteractionForm: Build Interactive Memory Timeline with 'Why?' Evidence, Add Interaction Form with Hindsight Retain, and AI Follow-Up Generator.
- **Status:** COMPLETED
- **Updates:** Successfully built Interactive Memory Timeline, Add Interaction Form with Hindsight Retain, AI Follow-Up Generator, and Memory Impact / Learning Curve screens. Verified build and tests.
- **Acceptance Criteria:**
  - Memory timeline and Why evidence view implemented
  - Add interaction form with Hindsight retain working
  - AI follow-up message generator functional

### Task_4_AdaptiveUiRunAndVerify: Implement adaptive UI for phones and tablets using Material Adaptive Library / Compose window size classes. Run and verify application stability, ensure no crashes, and confirm feature alignment.
- **Status:** COMPLETED
- **Updates:** Successfully implemented adaptive UI with Compose Window Size Classes and Material Adaptive Library supporting phones and tablets, verified build and unit tests.
- **Acceptance Criteria:**
  - Adaptive UI supports phones and tablets seamlessly
  - make sure all existing tests pass
  - build pass
  - app does not crash
  - Instruct critic_agent to verify application stability and confirm user requirements alignment

### Task_5_AstraColorSystemAndDesignTokens: Implement ASTRA Premium Enterprise Color System (Ivory #F7F7F5, White #FFFFFF, Graphite #202123, Charcoal #343541, Mist #ECECEA, Slate #6B6E73, Sage #4F8F83, Teal #6FA89C) and typography/elevation design tokens across light and dark modes.
- **Status:** COMPLETED
- **Updates:** Successfully implemented ASTRA Premium Enterprise Color System in Color.kt and Theme.kt with light/dark color schemes and verified build and tests.
- **Acceptance Criteria:**
  - ASTRA premium enterprise color palette integrated into Material 3 theme
  - Light and dark mode themes configured with high fidelity
  - Project builds successfully

### Task_6_EnterpriseSaaSComponentAndScreenRedesign: Redesign all core screens (Landing, Premium Dashboard, Acme Deal Detail with tabs, AI Meeting Brief, Memory Timeline, Follow-Up Studio, Settings) to match Linear/Stripe/Notion enterprise SaaS aesthetic with ASTRA Sage primary buttons and memory badges.
- **Status:** COMPLETED
- **Updates:** Successfully redesigned all core screens to Linear/Stripe/Notion enterprise SaaS aesthetic with ASTRA Sage accents, Memory badges, AI insight cards, and polished tabbed navigation. Verified build and unit tests.
- **Acceptance Criteria:**
  - All core screens updated with ASTRA enterprise UI components
  - Linear/Stripe/Notion inspired clean typography and subtle borders/elevation applied
  - Project builds successfully

### Task_7_RunAndVerifyRedesign: Compile and test application via ./gradlew :app:assembleDebug and unit tests. Instruct critic_agent to verify application stability (no crashes), confirm alignment with user requirements, and report critical UI issues.
- **Status:** COMPLETED
- **Updates:** Successfully verified build and unit tests for Astra Premium UI/UX Redesign. All tasks complete.
- **Acceptance Criteria:**
  - make sure all existing tests pass
  - build pass
  - app does not crash
  - Instruct critic_agent to verify application stability, confirm alignment with enterprise UI requirements and report any critical issues

### Task_8_WarmCoralAndMinimalistOnboarding: Implement Warm Coral color system (#C96B5C primary, #E09A8F secondary, #9E4F45 dark, #F7F6F2 ivory background, #FFFFFF surface, #202020 text, #E5E3DD borders, #171717 dark mode), minimalist visual-first AI aesthetic with strict text limits and Lucide icons, and immersive landing screen with animated memory orb & 3-step onboarding / split-screen Auth flow.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Warm coral color system and dark mode fully integrated
  - Minimalist visual-first aesthetic with strict text limits applied
  - Immersive landing, 3-step onboarding, and split-screen auth flow implemented
  - Project builds successfully
- **StartTime:** 2026-09-28 22:35:19 IST

### Task_9_SimplifiedNavigationCommandCenterAndVerification: Implement simplified sidebar navigation (Home, Deals, Accounts, Memory, Insights, Settings, Profile), Command Center Home with quick actions and ASTRA Insight panel, Tabbed Deal Workspace & Memory Explorer, and conversational Add Experience capture flow. Run and verify application stability, ensure no crashes, and confirm alignment with user requirements.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Simplified sidebar navigation and Command Center home working
  - Tabbed deal workspace, memory explorer, and conversational add experience flow implemented
  - make sure all existing tests pass
  - build pass
  - app does not crash
  - Instruct critic_agent to verify application stability, confirm alignment with user requirements, and report critical UI issues

