package hr.tvz.android.trcak.briftreger.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import hr.tvz.android.trcak.briftreger.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginButtonLogin.setOnClickListener {
            Log.d("LoginActivity", "Attempt to login with ${binding.emailInputRegistration.text.toString()}")
        }
    }
}