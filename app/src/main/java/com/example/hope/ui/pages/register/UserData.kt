package com.example.hope.ui.pages.register

data class UserData(
    var userID: String = "",
    var email: String = "",
    var username: String = "",
    var birthDate: String = "",
    var phoneNumber: String = "",
    var avatarID: Int? = null,
    var role: Role = Role.Regular
)

enum class Role {
    Regular,
    Psikolog
}
