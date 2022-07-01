package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ChatToRowBinding


class ChatToItem: BindableItem<ChatToRowBinding>() {
    override fun bind(viewBinding: ChatToRowBinding, position: Int) {
    }

    override fun getLayout() = R.layout.chat_to_row

    override fun initializeViewBinding(view: View): ChatToRowBinding {
        return ChatToRowBinding.bind(view)
    }
}