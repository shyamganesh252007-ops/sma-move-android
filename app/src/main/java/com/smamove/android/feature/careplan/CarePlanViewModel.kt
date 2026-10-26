
package com.smamove.android.feature.careplan

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class CarePlanTask(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val duration: String,
    val isCompleted: Boolean = false
)

class CarePlanViewModel : ViewModel() {

    private val _tasks = mutableStateListOf(
        CarePlanTask(
            id = 1,
            title = "Morning Mobility Stretches",
            description = "Demo task. Follow your clinician's instructions.",
            category = "Mobility",
            duration = "15 mins",
            isCompleted = true
        ),
        CarePlanTask(
            id = 2,
            title = "Respiratory Deep Breathing",
            description = "Demo task. Use only your prescribed care plan.",
            category = "Respiratory",
            duration = "3 sets"
        ),
        CarePlanTask(
            id = 3,
            title = "Hydration Milestone",
            description = "Example hydration reminder; personalize with your care team.",
            category = "Daily wellbeing",
            duration = "Daily"
        ),
        CarePlanTask(
            id = 4,
            title = "Care Plan Review",
            description = "Review instructions supplied by your healthcare team.",
            category = "Care coordination",
            duration = "As scheduled"
        ),
        CarePlanTask(
            id = 5,
            title = "Wellbeing Journal",
            description = "Record how you feel today.",
            category = "Wellbeing",
            duration = "5 mins",
            isCompleted = true
        )
    )

    val tasks: List<CarePlanTask>
        get() = _tasks

    val completedCount: Int
        get() = _tasks.count { it.isCompleted }

    fun toggleTask(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index >= 0) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }
}
