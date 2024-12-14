//package com.example.hope.chat
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.hope.ui.pages.register.UserData
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
//
//
//
//class GetClientRefreshViewModel(application: Application) : AndroidViewModel(application) {
//    private val db = FirebaseFirestore.getInstance()
//
//    // State untuk menyimpan daftar client
//    private val _clientList = MutableStateFlow<List<UserData>>(emptyList())
//    val clientList: StateFlow<List<UserData>> = _clientList
//
//    // Fungsi untuk mendapatkan daftar client berdasarkan toPsikologID
//    fun getClientsForPsikolog(psikologId: String) {
//        viewModelScope.launch {
//            try {
//                // Referensi ke koleksi "chats"
//                val chatsRef = db.collection("chats")
//
//                // Ambil semua dokumen dalam koleksi "chats"
//                val chatsSnapshot = chatsRef.get().await()
//
//                // Inisialisasi daftar client
//                val clients = mutableListOf<UserData>()
//
//                for (chat in chatsSnapshot.documents) {
//                    // Akses subkoleksi "messages" dalam setiap dokumen chats
//                    val messagesRef = chat.reference.collection("messages")
//
//                    // Query ke subkoleksi messages berdasarkan toPsikologID
//                    val messagesSnapshot = messagesRef
//                        .whereEqualTo("toPsikologID", psikologId)
//                        .get()
//                        .await()
//
//                    for (message in messagesSnapshot.documents) {
//                        // Ambil fromClientId untuk client yang ditemukan
//                        val clientId = message.getString("fromClientId")
//                        if (clientId != null) {
//                            clients.add(
//                                UserData(
//                                    userID = clientId,
//                                    username = "Client $clientId" // Placeholder username
//                                )
//                            )
//                        }
//                    }
//                }
//
//                // Update state dengan daftar client yang ditemukan
//                _clientList.value = clients
//
//                // Debugging: Log hasil
//                println("Jumlah client ditemukan: ${clients.size}")
//            } catch (e: Exception) {
//                // Tangani error
//                e.printStackTrace()
//                _clientList.value = emptyList()
//            }
//        }
//    }
//}


package com.example.hope.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//class GetClientRefreshViewModel(application: Application) : AndroidViewModel(application) {
//    private val db = FirebaseFirestore.getInstance()
//
//    // State untuk menyimpan daftar client
//    private val _clientList = MutableStateFlow<List<UserData>>(emptyList())
//    val clientList: StateFlow<List<UserData>> = _clientList
//
//    // Fungsi untuk mendapatkan seluruh data chat seadanya
//    fun getAllClients(psikologID: String) {
//        viewModelScope.launch {
//            try {
//                // Referensi ke koleksi "chats"
//                val chatsRef = db.collection("chats")
//
//                // Ambil semua dokumen dalam koleksi "chats"
//                val chatsSnapshot = chatsRef.get().await()
//
//                println("Jumlah dokumen di chats: ${chatsSnapshot.documents.size}")
//
//                val clients = mutableListOf<UserData>()
//
//                for (chat in chatsSnapshot.documents) {
//                    // Akses subkoleksi "messages" dalam setiap dokumen chats
//                    val messagesRef = chat.reference.collection("$psikologID")
//
//                    // Ambil semua dokumen dari subkoleksi messages tanpa filter
//                    val messagesSnapshot = messagesRef.get().await()
//
//                    println("Jumlah pesan ditemukan dalam chat: ${messagesSnapshot.documents.size}")
//
//                    for (message in messagesSnapshot.documents) {
//                        println("Data pesan: ${message.data}") // Debug semua data pesan
//
//                        // Ambil fromClientId untuk client yang ditemukan
//                        val clientId = message.getString("fromClientId")
//                        val clientUserName = message.getString("fromClientName")
//                        val avatarIdString = message.getString("fromClientAvatarId")
//                        val avatarId = avatarIdString?.toIntOrNull() ?: 0 // Convert to Integer, default 0 if invalid
//
//                        if (clientId != null) {
//                            clients.add(
//                                UserData(
//                                    userID = clientId,
//                                    username = clientUserName!!,
//                                    avatarID = avatarId, // Add avatar ID here
//
//                                )
//                            )
//                        }
//                    }
//                }
//
//                // Update state dengan daftar client yang ditemukan
//                _clientList.value = clients
//
//                // Debugging: Log hasil akhir
//                println("Jumlah client ditemukan: ${clients.size}")
//            } catch (e: Exception) {
//                // Tangani error
//                e.printStackTrace()
//                _clientList.value = emptyList()
//            }
//        }
//    }
//}


//class GetClientRefreshViewModel(application: Application) : AndroidViewModel(application) {
//    private val db = FirebaseFirestore.getInstance()
//
//    // State untuk menyimpan daftar client
//    private val _clientList = MutableStateFlow<List<UserData>>(emptyList())
//    val clientList: StateFlow<List<UserData>> = _clientList
//
//    // Fungsi untuk mendapatkan seluruh data chat seadanya
//    fun getAllClients(psikologID: String) {
//        viewModelScope.launch {
//            try {
//                // Referensi ke koleksi "chats"
//                val chatsRef = db.collection("chats")
//
//                // Ambil semua dokumen dalam koleksi "chats"
//                val chatsSnapshot = chatsRef.get().await()
//
//                println("Jumlah dokumen di chats: ${chatsSnapshot.documents.size}")
//
//                val clients = mutableListOf<UserData>()
//
//                for (chat in chatsSnapshot.documents) {
//                    // Akses subkoleksi berdasarkan psikologID
//                    val messagesRef = chat.reference.collection("$psikologID")
//
//                    // Ambil semua dokumen dari subkoleksi messages tanpa filter
//                    val messagesSnapshot = messagesRef.get().await()
//
//                    println("Jumlah pesan ditemukan dalam chat: ${messagesSnapshot.documents.size}")
//
//                    for (message in messagesSnapshot.documents) {
//                        println("Data pesan: ${message.data}") // Debug semua data pesan
//
//                        // Ambil fromClientId untuk client yang ditemukan
//                        val clientId = message.getString("fromClientId")
//                        val clientUserName = message.getString("fromClientName")
//                        val avatarIdString = message.getString("fromClientAvatarId")
//                        val avatarId = avatarIdString?.toIntOrNull() ?: 0 // Convert to Integer, default 0 if invalid
//
//                        if (clientId != null) {
//                            // Ambil data lengkap client dari koleksi "users" berdasarkan clientId
//                            val clientData = getClientData(clientId)
//
//                            // Update daftar client dengan data lengkap
//                            if (clientData != null) {
//                                clients.add(
//                                    UserData(
//                                        userID = clientData.userID,
//                                        username = clientData.username,
//                                        avatarID = clientData.avatarID // Add avatar ID here
//                                    )
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Update state dengan daftar client yang ditemukan
//                _clientList.value = clients
//
//                // Debugging: Log hasil akhir
//                println("Jumlah client ditemukan: ${clients.size}")
//            } catch (e: Exception) {
//                // Tangani error
//                e.printStackTrace()
//                _clientList.value = emptyList()
//            }
//        }
//    }
//
//    // Fungsi untuk mengambil data lengkap client berdasarkan clientId
//    private suspend fun getClientData(clientId: String): UserData? {
//        return try {
//            // Referensi ke koleksi "users" untuk mendapatkan data lengkap client
//            val userRef = db.collection("users").document(clientId)
//            val userSnapshot = userRef.get().await()
//
//            if (userSnapshot.exists()) {
//                val username = userSnapshot.getString("username") ?: "Unknown"
//                val avatarId = userSnapshot.getString("avatarId")?.toIntOrNull() ?: 0
//                UserData(
//                    userID = clientId,
//                    username = username,
//                    avatarID = avatarId
//                )
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            // Tangani error saat mengambil data client
//            e.printStackTrace()
//            null
//        }
//    }
//}




class GetClientRefreshViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()

    // State untuk menyimpan daftar client
    private val _clientList = MutableStateFlow<List<UserData>>(emptyList())
    val clientList: StateFlow<List<UserData>> = _clientList

    // Fungsi untuk mendapatkan seluruh data chat seadanya
    fun getAllClients(psikologID: String) {

        viewModelScope.launch {
            try {
                // Referensi ke koleksi "chats"
                val chatsRef = db.collection("chats")

                // Ambil semua dokumen dalam koleksi "chats"
                val chatsSnapshot = chatsRef.get().await()

                // Debugging: Log jumlah dokumen yang ada di koleksi chats
                println("Jumlah dokumen di chats: ${chatsSnapshot.documents.size}")

                val clients = mutableListOf<UserData>()

                // Loop untuk setiap dokumen chat
                for (chat in chatsSnapshot.documents) {
                    // Akses subkoleksi "messages" dalam setiap dokumen chats
                    val messagesRef = chat.reference.collection(psikologID)

                    // Ambil semua dokumen dari subkoleksi messages tanpa filter
                    val messagesSnapshot = messagesRef.get().await()

                    // Debugging: Log jumlah pesan yang ditemukan
                    println("Jumlah pesan ditemukan dalam chat: ${messagesSnapshot.documents.size}")

                    for (message in messagesSnapshot.documents) {
                        // Ambil fromClientId untuk client yang ditemukan
                        val clientId = message.getString("fromClientId")
                        val clientUserName = message.getString("fromClientName")
                        val avatarIdString = message.getString("fromClientAvatarId")
                        val avatarId = avatarIdString?.toIntOrNull() ?: 0 // Convert to Integer, default 0 if invalid

                        // Debugging: Log data yang diambil dari pesan
                        println("Data pesan: ${message.data}")

                        if (clientId != null && clientUserName != null) {
                            clients.add(
                                UserData(
                                    userID = clientId,
                                    username = clientUserName,
                                    avatarID = avatarId
                                )
                            )
                        }
                    }
                }

                // Update state dengan daftar client yang ditemukan
                _clientList.value = clients

                // Debugging: Log jumlah client yang ditemukan
                println("Jumlah client ditemukan: ${clients.size}")
            } catch (e: Exception) {
                // Tangani error
                e.printStackTrace()
                _clientList.value = emptyList()
            }
        }
    }
}
