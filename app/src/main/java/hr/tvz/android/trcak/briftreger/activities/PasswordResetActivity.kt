package hr.tvz.android.trcak.briftreger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ActivityPasswordResetBinding

class PasswordResetActivity : AppCompatActivity() {

    private var TAG = "PasswordResetActivity"

    private lateinit var binding: ActivityPasswordResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.resetButtonReset.setOnClickListener {
            val email = binding.emailInputReset.text.toString()

            if (email.isBlank()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Log.d(TAG, "Attempt to reset password for: $email")

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    Log.d(TAG, "passwordResetRequest:success ")
                    Toast.makeText(this, "Request successfully sent", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to send password reset request ")
                }
        }
    }
}