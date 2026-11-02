package com.smamove.android.navigation

object Routes {
    // Role selection & Auth
    const val ROLE_SELECTION = "role_selection"
    const val PATIENT_LOGIN = "patient_login"
    const val FAMILY_LOGIN = "family_login"
    const val PROFESSIONAL_LOGIN = "professional_login"

    // Patient Experience (1 - 9)
    const val PATIENT_DASHBOARD = "patient_dashboard"
    const val CARE_PLAN = "care_plan"
    const val DAILY_CARE_PLAN_DETAILS = "daily_care_plan_details"
    const val CARE_TASK_DETAILS = "care_task_details/{taskId}"
    fun careTaskDetails(taskId: Int) = "care_task_details/$taskId"
    const val WELLBEING_JOURNAL = "wellbeing_journal"
    const val WELLBEING_JOURNAL_SYMPTOM = "wellbeing_journal_symptom"
    const val CHECK_IN_CONFIRMATION = "check_in_confirmation"
    const val APPOINTMENT_DETAILS = "appointment_details"
    const val PROFILE_SETTINGS = "profile_settings"

    // MoveTrack & Privacy (10 - 14)
    const val MOVETRACK_CONSENT = "movetrack_consent"
    const val MOVETRACK_SESSION_INFO = "movetrack_session_info"
    const val MOVETRACK_DETAILED_CONSENT = "movetrack_detailed_consent"
    const val MOVETRACK_CONSENT_OPTIONS = "movetrack_consent_options"
    const val MOVETRACK_PRIVACY_SETTINGS = "movetrack_privacy_settings"
    const val MOVETRACK_SESSION_SUMMARY = "movetrack_session_summary"

    // Family and Caregiver (15 - 19)
    const val CAREGIVER_DASHBOARD = "caregiver_dashboard"
    const val INVITE_CAREGIVER = "invite_caregiver"
    const val REVIEW_INVITATION = "review_invitation"
    const val SHARED_CARE_UPDATES = "shared_care_updates"
    const val MANAGE_CONNECTED_CAREGIVERS = "manage_connected_caregivers"

    // Healthcare Professional (20 - 24)
    const val PROFESSIONAL_REGISTRATION = "professional_registration"
    const val PROFESSIONAL_VERIFICATION_SUBMISSION = "professional_verification_submission"
    const val PROFESSIONAL_VERIFICATION_PENDING = "professional_verification_pending"
    const val PROFESSIONAL_PROFILE = "professional_profile"

    // Accessibility and System Screens (25 - 29)
    const val ACCESSIBILITY_SETTINGS = "accessibility_settings"
    const val LIVE_CONTRAST_PREVIEW = "live_contrast_preview"
    const val NOTIFICATION_PREFERENCES = "notification_preferences"
    const val ACCOUNT_SECURITY_SETTINGS = "account_security_settings"
    const val SYSTEM_STATES = "system_states"
}
