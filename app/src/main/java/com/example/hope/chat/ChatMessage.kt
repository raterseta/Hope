package com.example.hope.chat

data class ChatMessage(
    val id: String = "", // ID unik
    val isUser: Boolean = false, // Apakah pesan dari pengguna
    val message: String = "", // Isi pesan
    val timestamp: Long = System.currentTimeMillis() // Waktu pesan dikirim
)

