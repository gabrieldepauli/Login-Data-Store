package br.edu.ifsp.dmo1.logindatastore.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo1.logindatastore.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.logindatastore.ui.logged.LoggedActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loggedIn.observe(this, Observer {
            if (it) {
                navigateToLoggedActivity()
            } else {
                Toast.makeText(this, "Erro ao fazer login.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loginPreferences.observe(this, Observer {
            val (saveLogin, stayLoggeIn) = it
            if (stayLoggeIn) {
                navigateToLoggedActivity()
            }
            binding.checkboxSaveLogin.isChecked = saveLogin
            binding.checkboxStayLoggedin.isChecked = stayLoggeIn
        })

        viewModel.dataPreferences.observe(this, Observer {
            val (email, password) = it
            binding.textEmail.setText(email)
            if (email.isNotEmpty())
                binding.textPassword.setText(password.toString())
        })
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = binding.textEmail.text.toString()
        val passwd = binding.textPassword.text.toString().toLong()
        val saveLogin = binding.checkboxSaveLogin.isChecked
        val stayLoggedIn = binding.checkboxStayLoggedin.isChecked

        binding.textEmail.setText("")
        binding.textPassword.setText("")
        binding.checkboxSaveLogin.isChecked = false
        binding.checkboxStayLoggedin.isChecked = false

        if (email.isNotEmpty()) {
            viewModel.login(email, passwd, saveLogin, stayLoggedIn)
        } else {
            Toast.makeText(this, "Preencha todos os dados.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLoggedActivity() {
        if (!flag) {
            flag = true
            val mIntent = Intent(this, LoggedActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }
}