package com.example.hope.chat

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ContentChatViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Pair<Boolean, String>>()
    val messages: List<Pair<Boolean, String>> get() = _messages

    var isBotTyping by mutableStateOf(false)
        private set

    private val botResponses = listOf(
        "Interesting, tell me more!",
        "That sounds cool.",
        "I'm listening...",
        "Hmm, interesting perspective.",
        "Really? Can you elaborate?",
        "I see what you mean.",
        "That's an intriguing point.",
        "Let me think about that.",
        "Wow, fascinating!",
        "Continue..."
    )

    fun sendMessage(message: String) {
        _messages.add(true to message)
        simulateBotResponse()
    }

    private fun simulateBotResponse() {
        viewModelScope.launch {
            isBotTyping = true
            delay(Random.nextLong(500, 2000))
            val botResponse = botResponses.random()
            _messages.add(false to botResponse)
            isBotTyping = false
        }
    }

    init {
        _messages.addAll(
            listOf(
                true to "Hello there!",
                false to "Hi! How are you today?",
                true to "I'm doing great, thanks for asking.",
                false to "Glad to hear that!"
            )
        )
    }
}