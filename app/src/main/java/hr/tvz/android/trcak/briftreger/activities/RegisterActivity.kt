package hr.tvz.android.trcak.briftreger.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import hr.tvz.android.trcak.briftreger.databinding.ActivityRegisterBinding
import hr.tvz.android.trcak.briftreger.models.User
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val TAG = "RegisterActivity"
    private var selectedPhotoUri: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.selectPhotoRegistration.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")
            openImageSelector()
        }

        binding.registerButtonRegistration.setOnClickListener {
            registerNewUser()
        }

        binding.haveAccountInputRegistration.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

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

        Log.d(TAG, "Email is: $email")
        Log.d(TAG, "Email is: $pass")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d(TAG, "Successfully created user with uid: ${it.result.user?.uid}")
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference.child("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {it2 ->
                    Log.d(TAG, "File location: $it2")
                    saveUserToFirebaseDatabase(it2.toString())
                }
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to upload image: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        Log.d(TAG, "Trying to save user in saveUserToFirebaseDatabase")
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().reference.child("users").child(uid)

        val user = User(uid, binding.usernameInputRegistration.text.toString(), profileImageUrl)
        Log.d(TAG, "User is $user")

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "User saved to Firebase Database")

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to save user to Firebase Database, reason: $it")
            }
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it != null) {

            selectedPhotoUri = it.data?.data
            Log.d(TAG, "Photo is selected $selectedPhotoUri")

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            binding.circularImagePreview.setImageBitmap(bitmap)
            binding.selectPhotoRegistration.alpha = 0f
            //val bitmapDrawable = BitmapDrawable(resources, bitmap)

            /*val bitmap = uri?.let { it1 -> ImageDecoder.createSource(contentResolver, it1) }
            val bitmapDrawable = Drawable.createFromPath(uri.toString())*/

            //binding.selectPhotoRegistration.background = bitmapDrawable
        } else {
            Log.d(TAG, "Photo NOT selected")
        }
    }
}