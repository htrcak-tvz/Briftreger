package hr.tvz.android.trcak.briftreger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupieAdapter
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ActivityLatestMessagesBinding
import hr.tvz.android.trcak.briftreger.models.LatestMessageItem
import hr.tvz.android.trcak.briftreger.models.User

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        lateinit var currentUser: User
    }

    lateinit var binding: ActivityLatestMessagesBinding
    private val TAG = "LatestMessagesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMessagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.newMessageButtonLatestMessages.setOnClickListener {
            openListOfUsers()
        }

        isUserLoggedIn()
        setupDummyRows()

    }

    private fun setupDummyRows() {
        val adapter = GroupieAdapter()

        adapter.add(LatestMessageItem())
        adapter.add(LatestMessageItem())
        adapter.add(LatestMessageItem())

        binding.RVLatestMessages.adapter = adapter
    }

    private fun openListOfUsers() {
        val intent = Intent(this, NewMessageActivity::class.java)
        startActivity(intent)
    }

    private fun isUserLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            loadRegisterActivity()
        } else {
            getCurrentUser(uid)
        }
    }

    private fun getCurrentUser(uid: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)!!
                Log.d(TAG, "User logged in: $currentUser")
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun loadRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                loadRegisterActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}