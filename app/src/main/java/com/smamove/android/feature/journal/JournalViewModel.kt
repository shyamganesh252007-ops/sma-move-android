
package com.smamove.android.feature.journal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class JournalEntry(
    val id: Long,
    val mood: String,
    val comfortLevel: String,
    val notes: String,
    val timestamp: Long
)

class JournalViewModel : ViewModel() {

    private val _entries = mutableStateListOf<JournalEntry>()

    val entries: List<JournalEntry>
        get() = _entries

    fun saveEntry(
        mood: String,
        comfortLevel: String,
        notes: String
    ) {
        _entries.add(
            0,
            JournalEntry(
                id = System.currentTimeMillis(),
                mood = mood,
                comfortLevel = comfortLevel,
                notes = notes.trim(),
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun formatDate(timestamp: Long): String {
        return SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date(timestamp))
    }
}
