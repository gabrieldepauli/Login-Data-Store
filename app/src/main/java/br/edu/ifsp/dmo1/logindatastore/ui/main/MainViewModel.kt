package br.edu.ifsp.dmo1.logindatastore.ui.main

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import br.edu.ifsp.dmo1.logindatastore.data.DataStoreRepository
import br.edu.ifsp.dmo1.logindatastore.data.User
import br.edu.ifsp.dmo1.logindatastore.ui.logged.LoggedActivity
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val loginPreferences: LiveData<Pair<Boolean, Boolean>> = repository.loginPreferences.asLiveData()
    val dataPreferences: LiveData<Pair<String, Long>> = repository.dataPreferences.asLiveData()
    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    fun login(email: String, passwd: Long, saveLogin: Boolean, stayLoggedIn: Boolean) {
        if (User.autenticate(email, passwd)) {
            _loggedIn.value = true
            if (saveLogin || stayLoggedIn)
                savePreferences(email, passwd, saveLogin, stayLoggedIn)
            else
                savePreferences("", 0L, saveLogin, stayLoggedIn)

            val intent = Intent(getApplication<Application>(), LoggedActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            getApplication<Application>().startActivity(intent)

        } else {
            _loggedIn.value = false
        }
    }

    private fun savePreferences(email: String, password: Long, saveLogin: Boolean, stayLoggedIn: Boolean) {
        viewModelScope.launch {
            repository.savePreferences(email, password, saveLogin, stayLoggedIn)
        }
    }

}