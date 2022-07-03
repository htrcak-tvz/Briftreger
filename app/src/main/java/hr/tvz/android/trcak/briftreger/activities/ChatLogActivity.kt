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
import hr.tvz.android.trcak.briftreger.adapters.ChatItems
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
        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/${LatestMessagesActivity.currentUser.uid}/${toUser.uid}")

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

                binding.RVChatLog.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage() {

        val text = binding.editTextChatLog.text.toString()
        val senderId = FirebaseAuth.getInstance().uid
        val receiverId = toUser.uid

        if (senderId == null) return
        if (receiverId == null) return

        val senderReference = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/$senderId/$receiverId").push()

        val receiverReference = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/$receiverId/$senderId").push()

        val latestMessageSenderReference = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$senderId/$receiverId")

        val latestMessageReceiverReference = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$receiverId/$senderId")

        val chatMessage = ChatMessage(senderReference.key!!, text, senderId, receiverId,
            (System.currentTimeMillis() / 1000)
        )

        Log.d(TAG, "Chat message: $chatMessage")

        senderReference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved chat message: ${senderReference.key}")
                binding.editTextChatLog.text.clear()
                binding.RVChatLog.scrollToPosition(adapter.itemCount - 1)
            }

        receiverReference.setValue(chatMessage)

        latestMessageSenderReference.setValue(chatMessage)
        latestMessageReceiverReference.setValue(chatMessage)
    }

}