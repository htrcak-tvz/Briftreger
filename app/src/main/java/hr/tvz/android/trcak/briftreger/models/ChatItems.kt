package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ChatFromRowBinding
import hr.tvz.android.trcak.briftreger.databinding.ChatToRowBinding

class ChatItems {

    class ChatFromItem(val text: String): BindableItem<ChatFromRowBinding>() {

        override fun bind(viewBinding: ChatFromRowBinding, position: Int) {
            viewBinding.textView.text = text
        }

        override fun getLayout() = R.layout.chat_from_row

        override fun initializeViewBinding(view: View): ChatFromRowBinding {
            return ChatFromRowBinding.bind(view)
        }
    }

    class ChatToItem(val text: String): BindableItem<ChatToRowBinding>() {

        override fun bind(viewBinding: ChatToRowBinding, position: Int) {
            viewBinding.textView.text = text
        }

        override fun getLayout() = R.layout.chat_to_row

        override fun initializeViewBinding(view: View): ChatToRowBinding {
            return ChatToRowBinding.bind(view)
        }
    }
}
