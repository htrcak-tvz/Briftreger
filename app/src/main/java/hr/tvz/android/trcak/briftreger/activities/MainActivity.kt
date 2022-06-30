package hr.tvz.android.trcak.briftreger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import hr.tvz.android.trcak.briftreger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registerButtonRegistration.setOnClickListener {
            val email = binding.emailInputRegistration.text.toString()
            val pass = binding.passwordInputRegistration.text.toString()

            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Email is: $pass")
        }

        binding.haveAccountInputRegistration.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}