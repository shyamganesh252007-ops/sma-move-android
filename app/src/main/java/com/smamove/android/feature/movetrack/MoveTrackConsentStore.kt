
package com.smamove.android.feature.movetrack

import android.content.Context

data class MoveTrackConsentRecord(
    val cameraOptIn: Boolean,
    val caregiverSharingOptIn: Boolean,
    val consentVersion: String,
    val updatedAtMillis: Long
)

class MoveTrackConsentStore(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    fun saveChoices(
        cameraOptIn: Boolean,
        caregiverSharingOptIn: Boolean
    ): MoveTrackConsentRecord {
        val record = MoveTrackConsentRecord(
            cameraOptIn = cameraOptIn,
            caregiverSharingOptIn = caregiverSharingOptIn,
            consentVersion = CONSENT_VERSION,
            updatedAtMillis = System.currentTimeMillis()
        )

        val saved = preferences.edit()
            .putBoolean(KEY_CAMERA_OPT_IN, record.cameraOptIn)
            .putBoolean(
                KEY_CAREGIVER_SHARING,
                record.caregiverSharingOptIn
            )
            .putString(
                KEY_CONSENT_VERSION,
                record.consentVersion
            )
            .putLong(
                KEY_UPDATED_AT,
                record.updatedAtMillis
            )
            .commit()

        check(saved) {
            "Could not save MoveTrack consent preferences."
        }

        return record
    }

    fun getChoices(): MoveTrackConsentRecord? {
        if (!preferences.contains(KEY_UPDATED_AT)) {
            return null
        }

        return MoveTrackConsentRecord(
            cameraOptIn = preferences.getBoolean(
                KEY_CAMERA_OPT_IN,
                false
            ),
            caregiverSharingOptIn = preferences.getBoolean(
                KEY_CAREGIVER_SHARING,
                false
            ),
            consentVersion = preferences.getString(
                KEY_CONSENT_VERSION,
                CONSENT_VERSION
            ) ?: CONSENT_VERSION,
            updatedAtMillis = preferences.getLong(
                KEY_UPDATED_AT,
                0L
            )
        )
    }

    fun withdrawCameraConsent(): Boolean {
        return preferences.edit()
            .putBoolean(KEY_CAMERA_OPT_IN, false)
            .putLong(
                KEY_UPDATED_AT,
                System.currentTimeMillis()
            )
            .commit()
    }

    fun withdrawCaregiverSharing(): Boolean {
        return preferences.edit()
            .putBoolean(KEY_CAREGIVER_SHARING, false)
            .putLong(
                KEY_UPDATED_AT,
                System.currentTimeMillis()
            )
            .commit()
    }

    fun withdrawAllChoices(): Boolean {
        return preferences.edit()
            .putBoolean(KEY_CAMERA_OPT_IN, false)
            .putBoolean(KEY_CAREGIVER_SHARING, false)
            .putLong(
                KEY_UPDATED_AT,
                System.currentTimeMillis()
            )
            .commit()
    }

    fun clearChoices(): Boolean {
        return preferences.edit()
            .clear()
            .commit()
    }

    companion object {
        private const val PREFERENCES_NAME =
            "smamove_movetrack_consent"

        private const val KEY_CAMERA_OPT_IN =
            "camera_opt_in"

        private const val KEY_CAREGIVER_SHARING =
            "caregiver_sharing_opt_in"

        private const val KEY_CONSENT_VERSION =
            "consent_version"

        private const val KEY_UPDATED_AT =
            "updated_at_millis"

        const val CONSENT_VERSION = "1.0"
    }
}
