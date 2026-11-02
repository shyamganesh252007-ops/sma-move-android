
package com.smamove.android.feature.auth

import android.content.Context

/**
 * Stores the currently remembered demo session.
 *
 * Prototype only:
 * - Stores the user's role.
 * - Does not store passwords.
 * - Does not represent real backend authentication.
 *
 * Replace the demo session mechanism with server-issued
 * authentication tokens when the backend is integrated.
 */
class AuthSessionStore(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences(
            SESSION_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun saveSession(role: String) {
        preferences.edit()
            .putString(KEY_SESSION_ROLE, role)
            .putBoolean(KEY_SESSION_ACTIVE, true)
            .apply()
    }

    fun hasActiveSession(): Boolean {
        return preferences.getBoolean(KEY_SESSION_ACTIVE, false) &&
                getSessionRole() != null
    }

    fun getSessionRole(): String? {
        return preferences.getString(KEY_SESSION_ROLE, null)
    }

    fun clearSession() {
        preferences.edit()
            .clear()
            .apply()
    }

    companion object {
        private const val SESSION_PREFERENCES =
            "sma_move_session_preferences"

        private const val KEY_SESSION_ROLE = "session_role"
        private const val KEY_SESSION_ACTIVE = "session_active"

        const val ROLE_PATIENT = "patient"
        const val ROLE_CAREGIVER = "caregiver"
        const val ROLE_PROFESSIONAL = "professional"
    }
}
