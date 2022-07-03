package hr.tvz.android.trcak.briftreger.adapters

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.LatestMessagesRowBinding
import hr.tvz.android.trcak.briftreger.models.ChatMessage
import hr.tvz.android.trcak.briftreger.models.User

class LatestMessageItem(val chatMessage: ChatMessage): BindableItem<LatestMessagesRowBinding>() {

    var chatPartnerUser: User?= null

    override fun bind(viewBinding: LatestMessagesRowBinding, position: Int) {
        // todo implement avatar loading

        val chatPartnerId: String = if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatMessage.toId
        } else {
            chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser = snapshot.getValue(User::class.java)
                viewBinding.usernameLatestMessage.text = chatPartnerUser?.username
                viewBinding.imageViewNewMessage.setImageURI(chatPartnerUser?.profileImageUrl)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        viewBinding.messageLatestMessage.text = chatMessage.text
    }

    override fun getLayout() = R.layout.latest_messages_row

    override fun initializeViewBinding(view: View): LatestMessagesRowBinding {
        return LatestMessagesRowBinding.bind(view)
    }
}