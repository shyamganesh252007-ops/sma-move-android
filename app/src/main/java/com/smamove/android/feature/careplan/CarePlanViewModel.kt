package com.smamove.android.feature.careplan

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class CarePlanStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String
)

data class CarePlanTask(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val duration: String,
    val scheduledTime: String = "09:00 AM",
    val verifiedBy: String = "Physiotherapy Care Team",
    val steps: List<CarePlanStep> = emptyList(),
    val isCompleted: Boolean = false
)

class CarePlanViewModel : ViewModel() {

    private val _tasks = mutableStateListOf(
        CarePlanTask(
            id = 1,
            title = "Morning BiPAP & Cough Assist Protocol",
            description = "Maintenance therapy to clear airway secretions safely. [Demonstration Protocol - refer to individualized clinician prescription].",
            category = "Respiratory care",
            duration = "15 mins",
            scheduledTime = "09:00 AM",
            verifiedBy = "Dr. Sarah Jenkins (Prescribed Regimen)",
            steps = listOf(
                CarePlanStep(1, "Positioning & Comfort", "Ensure the patient is seated upright at a 45 to 90-degree angle with proper neck and head support before connecting the circuit."),
                CarePlanStep(2, "Individual Prescription Verification", "Verify device settings strictly match your pulmonologist's individualized written prescription. Never alter respiratory device pressures without clinical authorization."),
                CarePlanStep(3, "Cycle Execution", "Apply mask firmly over mouth and nose. Deliver prescribed cycles allowing adequate rest intervals between sets.")
            ),
            isCompleted = true
        ),
        CarePlanTask(
            id = 2,
            title = "Supine to Seated Shift & Stretch",
            description = "Gentle passive range-of-motion stretching for neck, shoulders, and lower extremities.",
            category = "Positioning",
            duration = "20 mins",
            scheduledTime = "02:00 PM",
            verifiedBy = "Physiotherapy Specialist",
            steps = listOf(
                CarePlanStep(1, "Gentle Upper Extremity Reach", "Slow passive elbow extension and wrist flexion with arm supported by pillows."),
                CarePlanStep(2, "Trunk Alignment", "Reposition wheelchair lumbar support to preserve neutral spinal alignment."),
                CarePlanStep(3, "Lower Limb Elevation", "Gentle ankle pumps to support healthy peripheral circulation.")
            ),
            isCompleted = false
        ),
        CarePlanTask(
            id = 3,
            title = "Afternoon Fluid Intake & Vitals",
            description = "Hydration tracking and pulse oximeter check to ensure steady oxygen saturation.",
            category = "Hydration",
            duration = "10 mins",
            scheduledTime = "04:30 PM",
            verifiedBy = "Clinical Nursing Coordinator",
            steps = listOf(
                CarePlanStep(1, "Hydration Milestone", "Consume 250ml water or electrolyte fluid with caregiver support."),
                CarePlanStep(2, "Resting SpO2", "Check fingertip pulse oximeter reading while resting quietly.")
            ),
            isCompleted = false
        ),
        CarePlanTask(
            id = 4,
            title = "Care Plan Review & Equipment Check",
            description = "Review mobility aids, battery levels, and suction tubing cleanliness.",
            category = "Care coordination",
            duration = "15 mins",
            scheduledTime = "07:00 PM",
            verifiedBy = "Care Coordinator",
            steps = listOf(
                CarePlanStep(1, "Device Battery Check", "Confirm backup power banks and wheelchair chargers are plugged in."),
                CarePlanStep(2, "Tubing Inspection", "Check filter and water trap on humidifier circuit.")
            ),
            isCompleted = false
        ),
        CarePlanTask(
            id = 5,
            title = "Wellbeing Journal Check-in",
            description = "Record today's comfort level, fatigue level, and personal observations.",
            category = "Wellbeing",
            duration = "5 mins",
            scheduledTime = "08:30 PM",
            verifiedBy = "Self-directed",
            steps = listOf(
                CarePlanStep(1, "Mood & Energy", "Select today's energy rating."),
                CarePlanStep(2, "Notes", "Add any notes on comfort or assistance needed.")
            ),
            isCompleted = true
        )
    )

    val tasks: List<CarePlanTask>
        get() = _tasks

    val completedCount: Int
        get() = _tasks.count { it.isCompleted }

    fun getTask(taskId: Int): CarePlanTask? {
        return _tasks.find { it.id == taskId }
    }

    fun toggleTask(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index >= 0) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun setTaskCompleted(taskId: Int, completed: Boolean) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index >= 0) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isCompleted = completed)
        }
    }
}
