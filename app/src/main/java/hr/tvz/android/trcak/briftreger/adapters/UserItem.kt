package hr.tvz.android.trcak.briftreger.adapters

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.UserRowNewMessageBinding
import hr.tvz.android.trcak.briftreger.models.User

class UserItem(val user: User): BindableItem<UserRowNewMessageBinding>() {

    override fun getLayout() = R.layout.user_row_new_message

    override fun bind(viewBinding: UserRowNewMessageBinding, position: Int) {
        viewBinding.usernameNewMessage.text = user.username
        viewBinding.imageViewNewMessage.setImageURI(user.profileImageUrl)
    }

    override fun initializeViewBinding(view: View): UserRowNewMessageBinding {
        return UserRowNewMessageBinding.bind(view)
    }


}