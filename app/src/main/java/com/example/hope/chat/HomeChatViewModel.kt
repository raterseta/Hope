//package com.example.hope.chat
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.State
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.android.gms.common.api.Api
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.launch
//
//// Gantilah Api.Client dengan tipe data yang sesuai, misalnya model Client yang Anda miliki.
//class HomeChatViewModel : ViewModel() {
//    private val _clientList = mutableStateOf<List<Api.Client>>(emptyList())
//    val clientList: State<List<Api.Client>> = _clientList
//
//    // Fungsi untuk mengubah daftar klien
//    fun setClientList(clients: List<Api.Client>) {
//        _clientList.value = clients
//    }
//
//    // Fungsi untuk refresh data klien
//    fun refreshClients(psikologID: String) {
//        viewModelScope.launch {
//            // Ambil data klien dari Firestore
//            val clients = getClientsFromFirestore(psikologID)
//            setClientList(clients)
//        }
//    }
//
//    // Fungsi untuk mengambil data klien dari Firestore
//    // Pastikan Anda mengganti ini dengan kode yang sesuai dengan implementasi Firestore Anda
//    private suspend fun getClientsFromFirestore(psikologID: String): List<Api.Client> {
//        val db = FirebaseFirestore.getInstance()
//        val snapshot = db.collection("$psikologID")
//            .whereEqualTo("psikologID", psikologID)
//            .get()
//            .await() // Menggunakan extension function await() dari Kotlin Coroutines untuk menunggu query Firestore selesai.
//
//        return snapshot.documents.map { document ->
//            Client(
//                id = document.id,
//                username = document.getString("username") ?: "Unknown",
//                avatarID = document.getLong("avatarID")?.toInt() ?: 0
//            )
//        }
//    }
//
//}
//
