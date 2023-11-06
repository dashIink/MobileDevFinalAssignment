package com.example.mobileappfinal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLoginButtonClick(view: View) {
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        when {
            isValidLogin(username, password) -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            isDevCredentials(username, password) -> {
                val intent = Intent(this, SecretButtonsActivity::class.java)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, "Invalid login credentials", Toast.LENGTH_LONG).show()
                usernameEditText.setTextColor(Color.RED)
                passwordEditText.setTextColor(Color.RED)
            }
        }
    }

    private fun isValidLogin(username: String, password: String): Boolean {
        return username == "username" && password == "password"
    }

    private fun isDevCredentials(username: String, password: String): Boolean {
        return username == "dev" && password == "dev"
    }
}
