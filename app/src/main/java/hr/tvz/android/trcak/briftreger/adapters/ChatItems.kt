package hr.tvz.android.trcak.briftreger.adapters

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ChatReceiverRowBinding
import hr.tvz.android.trcak.briftreger.databinding.ChatSenderRowBinding
import hr.tvz.android.trcak.briftreger.models.User

class ChatItems {

    class ChatReceiverItem(val text: String, val user: User): BindableItem<ChatReceiverRowBinding>() {

        override fun bind(viewBinding: ChatReceiverRowBinding, position: Int) {
            viewBinding.textView.text = text
            viewBinding.profileImage.setImageURI(user.profileImageUrl)
        }

        override fun getLayout() = R.layout.chat_receiver_row

        override fun initializeViewBinding(view: View): ChatReceiverRowBinding {
            return ChatReceiverRowBinding.bind(view)
        }
    }

    class ChatSenderItem(val text: String, val user: User): BindableItem<ChatSenderRowBinding>() {

        override fun bind(viewBinding: ChatSenderRowBinding, position: Int) {
            viewBinding.textView.text = text
            viewBinding.profileImage.setImageURI(user.profileImageUrl)
        }

        override fun getLayout() = R.layout.chat_sender_row

        override fun initializeViewBinding(view: View): ChatSenderRowBinding {
            return ChatSenderRowBinding.bind(view)
        }
    }
}
