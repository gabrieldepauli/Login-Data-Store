package br.edu.ifsp.dmo1.logindatastore.ui.logged

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import br.edu.ifsp.dmo1.logindatastore.data.DataStoreRepository
import kotlinx.coroutines.launch

class LoggedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    fun logout() {
        viewModelScope.launch {
            repository.savePreferences("", 0L, saveLogin = false, stayLoggedIn = false)
        }
    }

}