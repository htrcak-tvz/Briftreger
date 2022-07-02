package hr.tvz.android.trcak.briftreger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupieAdapter
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ActivityLatestMessagesBinding
import hr.tvz.android.trcak.briftreger.models.ChatMessage
import hr.tvz.android.trcak.briftreger.models.LatestMessageItem
import hr.tvz.android.trcak.briftreger.models.User
import hr.tvz.android.trcak.briftreger.models.UserItem

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        lateinit var currentUser: User
    }

    lateinit var binding: ActivityLatestMessagesBinding
    private val TAG = "LatestMessagesActivity"

    val adapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestMessagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.RVLatestMessages.adapter = adapter
        binding.RVLatestMessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, _ ->

            val intent = Intent(this, ChatLogActivity::class.java)

            val userItem = item as LatestMessageItem

            intent.putExtra(NewMessageActivity.USER_KEY, userItem.chatPartnerUser)
            startActivity(intent)
        }

        binding.newMessageButtonLatestMessages.setOnClickListener {
            openListOfUsers()
        }

        isUserLoggedIn()

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
            listenForLatestMessages(uid)
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

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun listenForLatestMessages(id: String) {
        //val id = FirebaseAuth.getInstance().uid // TODO why can't I take uid from global currentUser?
        val latestMessagesRef = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$id")

        latestMessagesRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return
                Log.d(TAG, "Message received: ${chatMessage.text}")

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageItem(it))
        }

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