package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.LatestMessagesRowBinding

class LatestMessageItem(val chatMessage: ChatMessage): BindableItem<LatestMessagesRowBinding>() {

    override fun bind(viewBinding: LatestMessagesRowBinding, position: Int) {
        // todo implement avatar loading
        viewBinding.usernameLatestMessage.text = "username"
        viewBinding.messageLatestMessage.text = chatMessage.text

    }

    override fun getLayout() = R.layout.latest_messages_row

    override fun initializeViewBinding(view: View): LatestMessagesRowBinding {
        return LatestMessagesRowBinding.bind(view)
    }
}