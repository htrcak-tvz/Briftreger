package hr.tvz.android.trcak.briftreger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupieAdapter
import hr.tvz.android.trcak.briftreger.databinding.ActivityChatLogBinding
import hr.tvz.android.trcak.briftreger.models.ChatItems
import hr.tvz.android.trcak.briftreger.models.ChatMessage
import hr.tvz.android.trcak.briftreger.models.User

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    private lateinit var binding: ActivityChatLogBinding
    private lateinit var toUser: User

    val adapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.RVChatLog.adapter = adapter

        toUser = intent.getParcelableExtra(NewMessageActivity.USER_KEY)!!
        supportActionBar?.title = toUser.username

        listenForMessages()

        binding.editTextSendButton.setOnClickListener {
            Log.d(TAG, "Attempting to send a message...")
            sendMessage()
        }
    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, "Message: ${chatMessage.text}")

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatItems.ChatSenderItem(chatMessage.text, LatestMessagesActivity.currentUser))
                    } else {
                        adapter.add(ChatItems.ChatReceiverItem(chatMessage.text, toUser))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage() {

        val text = binding.editTextChatLog.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser.uid

        if (fromId == null) return
        if (toId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId,
            (System.currentTimeMillis() / 1000)
        )

        Log.d(TAG, "Chat message: $chatMessage")

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved chat message: ${reference.key}")
            }

    }

}