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
    val timestamp: Long,
    val symptomNotes: String = "",
    val fatigueLevel: String = "Mild",
    val mobilityAssistanceNeeded: Boolean = false
)

class JournalViewModel : ViewModel() {

    private val _entries = mutableStateListOf<JournalEntry>()

    val entries: List<JournalEntry>
        get() = _entries

    val latestEntry: JournalEntry?
        get() = _entries.firstOrNull()

    fun saveEntry(
        mood: String,
        comfortLevel: String,
        notes: String,
        symptomNotes: String = "",
        fatigueLevel: String = "Mild",
        mobilityAssistanceNeeded: Boolean = false
    ): JournalEntry {
        val entry = JournalEntry(
            id = System.currentTimeMillis(),
            mood = mood,
            comfortLevel = comfortLevel,
            notes = notes.trim(),
            timestamp = System.currentTimeMillis(),
            symptomNotes = symptomNotes.trim(),
            fatigueLevel = fatigueLevel,
            mobilityAssistanceNeeded = mobilityAssistanceNeeded
        )
        _entries.add(0, entry)
        return entry
    }

    fun formatDate(timestamp: Long): String {
        return SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.ENGLISH
        ).format(Date(timestamp))
    }
}
