package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ChatFromRowBinding


class ChatFromItem: BindableItem<ChatFromRowBinding>() {
    override fun bind(viewBinding: ChatFromRowBinding, position: Int) {
    }

    override fun getLayout() = R.layout.chat_from_row

    override fun initializeViewBinding(view: View): ChatFromRowBinding {
        return ChatFromRowBinding.bind(view)
    }
}