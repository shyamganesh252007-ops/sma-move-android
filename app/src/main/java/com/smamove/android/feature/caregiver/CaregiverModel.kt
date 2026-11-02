package com.smamove.android.feature.caregiver

import androidx.compose.runtime.mutableStateListOf

data class CaregiverMember(
    val id: String,
    val name: String,
    val contact: String,
    val relation: String,
    val status: String, // "Active", "Pending Review", "Invited"
    val canViewCarePlan: Boolean = true,
    val canReceiveSummaries: Boolean = true,
    val canEditAssistance: Boolean = false,
    val addedDate: String = "24 Oct 2026"
)

object CaregiverStore {
    val caregivers = mutableStateListOf(
        CaregiverMember(
            id = "1",
            name = "Marcus Vance",
            contact = "+91 98765 43210",
            relation = "Primary Family Member / Caregiver",
            status = "Active",
            canViewCarePlan = true,
            canReceiveSummaries = true,
            canEditAssistance = true,
            addedDate = "10 Oct 2026"
        ),
        CaregiverMember(
            id = "2",
            name = "Priya Sharma",
            contact = "+91 98111 22334",
            relation = "Community Physiotherapy Aide",
            status = "Pending Review",
            canViewCarePlan = true,
            canReceiveSummaries = false,
            canEditAssistance = false,
            addedDate = "27 Oct 2026"
        )
    )

    fun addCaregiver(
        name: String,
        contact: String,
        relation: String,
        canViewCarePlan: Boolean,
        canReceiveSummaries: Boolean
    ): CaregiverMember {
        val member = CaregiverMember(
            id = System.currentTimeMillis().toString(),
            name = name.trim(),
            contact = contact.trim(),
            relation = relation,
            status = "Invited (Local Demo)",
            canViewCarePlan = canViewCarePlan,
            canReceiveSummaries = canReceiveSummaries,
            addedDate = "Today"
        )
        caregivers.add(0, member)
        return member
    }

    fun removeCaregiver(id: String) {
        caregivers.removeAll { it.id == id }
    }

    fun approveCaregiver(id: String) {
        val index = caregivers.indexOfFirst { it.id == id }
        if (index >= 0) {
            val item = caregivers[index]
            caregivers[index] = item.copy(status = "Active")
        }
    }
}

