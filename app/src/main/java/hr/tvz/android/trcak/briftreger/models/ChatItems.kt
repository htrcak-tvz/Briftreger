package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ChatReceiverRowBinding
import hr.tvz.android.trcak.briftreger.databinding.ChatSenderRowBinding

class ChatItems {

    class ChatReceiverItem(val text: String): BindableItem<ChatReceiverRowBinding>() {

        override fun bind(viewBinding: ChatReceiverRowBinding, position: Int) {
            viewBinding.textView.text = text
        }

        override fun getLayout() = R.layout.chat_receiver_row

        override fun initializeViewBinding(view: View): ChatReceiverRowBinding {
            return ChatReceiverRowBinding.bind(view)
        }
    }

    class ChatSenderItem(val text: String): BindableItem<ChatSenderRowBinding>() {

        override fun bind(viewBinding: ChatSenderRowBinding, position: Int) {
            viewBinding.textView.text = text
        }

        override fun getLayout() = R.layout.chat_sender_row

        override fun initializeViewBinding(view: View): ChatSenderRowBinding {
            return ChatSenderRowBinding.bind(view)
        }
    }
}
