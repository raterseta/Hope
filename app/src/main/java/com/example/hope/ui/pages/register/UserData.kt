package com.example.hope.ui.pages.register

data class UserData(
    var userID: String = "",
    var email: String = "",
    var username: String = "",
    var birthDate: String = "",
    var phoneNumber: String = "",
    var avatarID: Int? = null,
    var role: Role = Role.Regular,
    var psikologData: PsikologData? = null // Data khusus Psikolog, nullable untuk Regular
)

data class PsikologData(
    var startTime: String = "08:00", // Default "08:00" jika kosong
    var endTime: String = "16:00"    // Format waktu, misal "16:00"
)

enum class Role {
    Regular,
    Psikolog
}
