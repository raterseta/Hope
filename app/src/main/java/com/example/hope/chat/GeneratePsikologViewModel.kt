package com.example.hope.chat

import androidx.lifecycle.viewModelScope
import com.example.hope.ui.pages.register.Role
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

// Tambahkan dataStore instance ke aplikasi
private val Context.dataStore by preferencesDataStore("psikolog_preferences")

//class GeneratePsikologViewModel(application: Application) : AndroidViewModel(application) {
//    private val context = application
//
//    private val PSIKOLOG_KEY = stringPreferencesKey("active_psikolog")
//
//    val activePsikolog: StateFlow<UserData?> = context.dataStore.data
//        .map { preferences ->
//            preferences[PSIKOLOG_KEY]?.let { json ->
//                // Deserialize JSON back into UserData
//                Gson().fromJson(json, UserData::class.java)
//            }
//        }
//        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
//
//    fun generateActivePsikolog() {
//        viewModelScope.launch {
//            val database = FirebaseDatabase.getInstance().getReference("users")
//            val snapshot = database.get().await() // Ubah ini menjadi coroutine-friendly
//            val psikologList = snapshot.children.mapNotNull { it.getValue(UserData::class.java) }
//                .filter { user ->
//                    user.role == Role.Psikolog && isPsikologInactive(user)
////                    user.role == Role.Psikolog && isPsikologActive(user)
//                }
//
//            if (psikologList.isNotEmpty()) {
//                val randomPsikolog = psikologList[Random.nextInt(psikologList.size)]
//                saveActivePsikolog(randomPsikolog) // Ini sekarang aman dipanggil
//            }
//        }
//    }
//
//
//    private suspend fun saveActivePsikolog(psikolog: UserData) {
//        val json = Gson().toJson(psikolog)
//        context.dataStore.edit { preferences: MutablePreferences ->
//            preferences[PSIKOLOG_KEY] = json
//        }
//
//    }
//    private fun isPsikologActive(psikolog: UserData): Boolean {
//        val currentTime = System.currentTimeMillis()
//
//        val calendar = java.util.Calendar.getInstance()
//        val today = calendar.apply {
//            set(java.util.Calendar.HOUR_OF_DAY, 0)
//            set(java.util.Calendar.MINUTE, 0)
//            set(java.util.Calendar.SECOND, 0)
//            set(java.util.Calendar.MILLISECOND, 0)
//        }.timeInMillis
//
//        val startTimeInMillis = psikolog.psikologData?.startTime?.let { start ->
//            val (hour, minute) = start.split(":").map { it.toInt() }
//            today + hour * 60 * 60 * 1000 + minute * 60 * 1000
//        } ?: 0L
//
//        val endTimeInMillis = psikolog.psikologData?.endTime?.let { end ->
//            val (hour, minute) = end.split(":").map { it.toInt() }
//            today + hour * 60 * 60 * 1000 + minute * 60 * 1000
//        } ?: 0L
//
//        return currentTime in startTimeInMillis..endTimeInMillis
//    }
//    private fun isPsikologInactive(psikolog: UserData): Boolean {
//        val currentTime = System.currentTimeMillis()
//        val startTime = psikolog.psikologData?.startTime?.toLongOrNull() ?: 0L
//        val endTime = psikolog.psikologData?.endTime?.toLongOrNull() ?: 0L
//        return currentTime !in startTime..endTime
//    }
//}


class GeneratePsikologViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private val PSIKOLOG_KEY = stringPreferencesKey("active_psikolog")

    // MutableStateFlow untuk currentTime
    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime: StateFlow<Long> = _currentTime

    init {
        viewModelScope.launch {
            while (true) {
                _currentTime.value = System.currentTimeMillis()
                kotlinx.coroutines.delay(1000L) // Update setiap 1 detik
            }
        }
    }

    val activePsikolog: StateFlow<UserData?> = context.dataStore.data
        .map { preferences ->
            preferences[PSIKOLOG_KEY]?.let { json ->
                Gson().fromJson(json, UserData::class.java)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun generateActivePsikolog() {
        viewModelScope.launch {
            val database = FirebaseDatabase.getInstance().getReference("users")
            val snapshot = database.get().await()
            val psikologList = snapshot.children.mapNotNull { it.getValue(UserData::class.java) }
                .filter { user ->
                    user.role == Role.Psikolog && isPsikologInactive(user)
//                    user.role == Role.Psikolog && isPsikologActive(user)

                }

            if (psikologList.isNotEmpty()) {
                val randomPsikolog = psikologList[Random.nextInt(psikologList.size)]
                saveActivePsikolog(randomPsikolog)
            }
        }
    }

    private suspend fun saveActivePsikolog(psikolog: UserData) {
        val json = Gson().toJson(psikolog)
        context.dataStore.edit { preferences ->
            preferences[PSIKOLOG_KEY] = json
        }
    }

    private fun isPsikologActive(psikolog: UserData): Boolean {
        val currentTime = System.currentTimeMillis()

        val calendar = java.util.Calendar.getInstance()
        val today = calendar.apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }.timeInMillis

        val startTimeInMillis = psikolog.psikologData?.startTime?.let { start ->
            val (hour, minute) = start.split(":").map { it.toInt() }
            today + hour * 60 * 60 * 1000 + minute * 60 * 1000
        } ?: 0L

        val endTimeInMillis = psikolog.psikologData?.endTime?.let { end ->
            val (hour, minute) = end.split(":").map { it.toInt() }
            today + hour * 60 * 60 * 1000 + minute * 60 * 1000
        } ?: 0L

        return currentTime in startTimeInMillis..endTimeInMillis
    }

    private fun isPsikologInactive(psikolog: UserData): Boolean {
        val currentTime = System.currentTimeMillis()
        val startTime = psikolog.psikologData?.startTime?.toLongOrNull() ?: 0L
        val endTime = psikolog.psikologData?.endTime?.toLongOrNull() ?: 0L
        return currentTime !in startTime..endTime
    }

}
