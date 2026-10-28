package com.smamove.android.feature

import com.smamove.android.feature.careplan.CarePlanViewModel
import com.smamove.android.feature.journal.JournalViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardDataViewModelTest {

    @Test
    fun carePlanTaskChangesUpdateCompletionCount() {
        val viewModel = CarePlanViewModel()

        assertEquals(2, viewModel.completedCount)

        viewModel.toggleTask(taskId = 2)
        assertEquals(3, viewModel.completedCount)

        viewModel.toggleTask(taskId = 2)
        assertEquals(2, viewModel.completedCount)

        viewModel.toggleTask(taskId = -1)
        assertEquals(2, viewModel.completedCount)
    }

    @Test
    fun journalSavesTrimmedEntriesNewestFirst() {
        val viewModel = JournalViewModel()

        viewModel.saveEntry(
            mood = "Good",
            comfortLevel = "Comfortable",
            notes = "  First note  "
        )
        viewModel.saveEntry(
            mood = "Fair",
            comfortLevel = "Some discomfort",
            notes = "  Second note  "
        )

        assertEquals(2, viewModel.entries.size)
        assertEquals("Fair", viewModel.entries[0].mood)
        assertEquals("Second note", viewModel.entries[0].notes)
        assertEquals("Good", viewModel.entries[1].mood)
        assertEquals("First note", viewModel.entries[1].notes)
    }
}
