package com.smamove.android.feature.auth

class AuthRepository {
    // This is a prototype. We do not implement real auth yet.
    // In the future, this would integrate with Firebase Auth or another backend.
    
    suspend fun loginPatient(email: String, pass: String): Result<Unit> {
        if (!email.contains("@")) {
            return Result.failure(Exception("Invalid email format (Demo validation)"))
        }
        if (pass.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters"))
        }
        if (email == "demo@smamove.com" && pass == "password") {
            return Result.success(Unit) // Prototype dummy success for specific demo account
        }
        return Result.failure(Exception("Invalid credentials. Try demo@smamove.com / password"))
    }
    
    suspend fun validateInvitationCode(code: String): Result<Unit> {
        if (code == "SMA-1234") {
            return Result.success(Unit) // Prototype dummy success for specific code
        }
        return Result.failure(Exception("Invalid or expired invitation code. Try SMA-1234"))
    }
    
    suspend fun verifyProfessional(email: String, hprId: String): Result<Unit> {
        if (!email.contains("@")) {
            return Result.failure(Exception("Invalid email format (Demo validation)"))
        }
        if (!hprId.matches(Regex("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))) {
            return Result.failure(Exception("Invalid HPR ID format. Must be XXXX-XXXX-XXXX-XXXX"))
        }
        return Result.success(Unit) // Prototype dummy success (goes to pending state)
    }
}
