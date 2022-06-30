package hr.tvz.android.trcak.briftreger.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.decodeBitmap
import com.google.firebase.auth.FirebaseAuth
import hr.tvz.android.trcak.briftreger.databinding.ActivityRegisterBinding
import javax.xml.transform.Source

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.selectPhotoRegistration.setOnClickListener {
            Log.d("MainActivity", "Try to show photo selector")
            openImageSelector()
        }

        binding.registerButtonRegistration.setOnClickListener {
            registerNewUser()
        }

        binding.haveAccountInputRegistration.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerNewUser() {
        val email = binding.emailInputRegistration.text.toString()
        val pass = binding.passwordInputRegistration.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
            return
        }

        Log.d("MainActivity", "Email is: $email")
        Log.d("MainActivity", "Email is: $pass")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("MainActivity", "Successfilly created user with uid: ${it.result.user?.uid}")
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        // todo
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        resultLauncher.launch(intent)
    }

    var selectedPhotoUri: Uri?= null

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it != null) {

            val selectedPhotoUri = it.data?.data
            Log.d("RegisterActivity", "Photo is selected $selectedPhotoUri")

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            bitmap.setHasAlpha(true)
            val bitmapDrawable = BitmapDrawable(resources, bitmap)

            /*val bitmap = uri?.let { it1 -> ImageDecoder.createSource(contentResolver, it1) }
            val bitmapDrawable = Drawable.createFromPath(uri.toString())*/

            binding.selectPhotoRegistration.background = bitmapDrawable
        } else {
            Log.d("RegisterActivity", "Photo NOT selected")
        }
    }
}