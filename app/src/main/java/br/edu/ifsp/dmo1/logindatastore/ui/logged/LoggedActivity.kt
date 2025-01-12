package br.edu.ifsp.dmo1.logindatastore.ui.logged

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo1.logindatastore.ui.main.MainActivity
import br.edu.ifsp.dmo1.logindatastore.databinding.ActivityLoggedBinding

class LoggedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoggedBinding
    private lateinit var viewModel: LoggedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoggedViewModel::class.java)
        binding.textMessage.setText("Bem-Vindo")

        binding.logOutButton.setOnClickListener {
            viewModel.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}