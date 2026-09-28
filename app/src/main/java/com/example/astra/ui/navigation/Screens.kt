package com.example.astra.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AstraScreen {
    @Serializable
    data object Landing : AstraScreen

    @Serializable
    data object Onboarding : AstraScreen

    @Serializable
    data object Auth : AstraScreen

    @Serializable
    data object Dashboard : AstraScreen

    @Serializable
    data object Deals : AstraScreen

    @Serializable
    data object DealDetail : AstraScreen

    @Serializable
    data object Accounts : AstraScreen

    @Serializable
    data object Memory : AstraScreen

    @Serializable
    data object MemoryTimeline : AstraScreen

    @Serializable
    data object Impact : AstraScreen

    @Serializable
    data object MemoryImpact : AstraScreen

    @Serializable
    data object DealTimeMachine : AstraScreen

    @Serializable
    data object MeetingPrep : AstraScreen

    @Serializable
    data object MeetingBrief : AstraScreen

    @Serializable
    data object AddExperience : AstraScreen

    @Serializable
    data object LogInteraction : AstraScreen

    @Serializable
    data object FollowUpStudio : AstraScreen

    @Serializable
    data object Insights : AstraScreen

    @Serializable
    data object Settings : AstraScreen

    @Serializable
    data object Profile : AstraScreen
}
