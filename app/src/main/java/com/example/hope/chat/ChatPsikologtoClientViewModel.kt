package com.example.hope.chat


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.tasks.await

class ChatPsikologtoClientViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    // State untuk menyimpan pesan
    var messages by mutableStateOf<List<Message>>(listOf())

    // Fungsi untuk mengirim pesan
    // Fungsi untuk mengirim pesan dengan timestamp
    suspend fun sendMessage(chatId: String,message: String, currentUserID: String , currentUserAvatarID: Int , currentUsername: String ,activeClientID: String): Boolean {
        val psikologID = "$currentUserID"  // Ganti dengan logic untuk mendapatkan user ID yang valid
        val psikologAvatarId = "$currentUserAvatarID"
        val psikologName = "$currentUsername"
        val clientID = activeClientID


        val messageData = hashMapOf(
            "fromPsikologtId" to psikologID,
            "fromPsikologAvatarId" to psikologAvatarId,
            "fromPsikologName" to psikologName,
            "toClientID" to clientID,
            "message" to message,
            "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()  // Menambahkan timestamp
        )

        try {
            // Menyimpan pesan ke koleksi "messages" dalam dokumen "chats"
            db.collection("chats")
                .document("meow")
                .collection("$psikologID")
                .add(messageData)
                .await()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


    // Fungsi untuk mendengarkan pembaruan pesan secara real-time
    // Di ChatViewModel
    fun listenForMessages(chatId: String, psikologID: String,onMessagesUpdated: (List<Message>) -> Unit): ListenerRegistration {
        return db.collection("chats")
            .document("meow")
            .collection("$psikologID")
            .orderBy("timestamp")  // Urutkan berdasarkan timestamp yang terbaru
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    println("Error getting messages: $e")
                    return@addSnapshotListener
                }

                // Ambil semua pesan dari snapshot
                val updatedMessages = snapshot?.documents?.map { doc ->
                    val clientId = doc.getString("fromClientId") ?: ""
                    val message = doc.getString("message") ?: ""
                    val timestamp = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L
                    Message(clientId, message, timestamp)
                } ?: listOf()

                // Panggil callback untuk mengupdate UI dengan pesan baru
                onMessagesUpdated(updatedMessages)
            }
    }

}


